package at.tyron.vintagecraft;

import java.io.File;
import java.util.List;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import at.tyron.vintagecraft.World.VCraftWorld;
import at.tyron.vintagecraft.WorldGen.DynTreeGenerators;
import at.tyron.vintagecraft.WorldGen.GenLayerVC;
import at.tyron.vintagecraft.WorldGen.Helper.BlockStateSerializer;
import at.tyron.vintagecraft.WorldGen.Helper.DynTreeBranch;
import at.tyron.vintagecraft.WorldGen.Helper.DynTreeGen;
import at.tyron.vintagecraft.WorldGen.Helper.DynTreeRoot;
import at.tyron.vintagecraft.WorldGen.Helper.DynTreeTrunk;
import at.tyron.vintagecraft.WorldGen.Helper.NatFloat;
import at.tyron.vintagecraft.WorldGen.LayerTransform.*;
import at.tyron.vintagecraft.WorldProperties.Terrain.EnumCrustLayer;
import at.tyron.vintagecraft.WorldProperties.Terrain.EnumCrustLayerGroup;
import at.tyron.vintagecraft.WorldProperties.Terrain.EnumFlowerGroup;
import at.tyron.vintagecraft.WorldProperties.Terrain.EnumMaterialDeposit;
import at.tyron.vintagecraft.WorldProperties.Terrain.EnumRockType;
import at.tyron.vintagecraft.WorldProperties.Terrain.EnumTree;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraft.world.gen.NoiseGeneratorOctaves;

public class VintageCraftCommands extends CommandBase {

	@Override
	public String getName() {
		return "vcraft";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/vcraft genlayer [forest|continents|rock]";
	}

	
	// /vcraft genlayer forest
	
	void clearArea(World world, BlockPos center, int wdt, int hgt) {
		for (int x = -wdt/2; x < wdt; x++) {
			for (int z = -wdt/2; z < wdt; z++) {
				for (int y = 0; y < hgt; y++) {
					world.setBlockState(center.add(x, y, z), Blocks.air.getDefaultState());
				}
			}
		}
	}
	
	@Override
	public void execute(ICommandSender sender, String[] args) throws CommandException {
		World world = sender.getEntityWorld();
		
		if (args.length == 0) {
			sender.addChatMessage(new ChatComponentText(getCommandUsage(sender)));
			return;
		}
		
		if (args[0].equals("clear")) {
			int wdt = 30;
			int hgt = 80;
			
			clearArea(sender.getEntityWorld(), sender.getPosition(), wdt, hgt);
		}
		
		if (args[0].equals("gen")) {
			GsonBuilder builder = new GsonBuilder();
	        builder.registerTypeAdapter(IBlockState.class, new BlockStateSerializer());
	        
	        Gson gson = builder.create();
	        
			DynTreeGen gen = gson.fromJson(args[1], DynTreeGen.class); 
			
			gen.growTree(sender.getEntityWorld(), sender.getPosition().north());
		}
		
		
		if (args[0].equals("cgentree") || args[0].equals("cgenltree") || args[0].equals("cgenptree")) {
			args[0] = args[0].substring(1);
			int wdt = 30;
			int hgt = 80;
			
			clearArea(sender.getEntityWorld(), sender.getPosition(), wdt, hgt);	
		}
		
		
		if (args[0].equals("gentree") || args[0].equals("genltree") || args[0].equals("genptree")) {
			float size = 1f;
			float bend = 0f;
			EnumTree tree = EnumTree.SCOTSPINE;
			
			if (args.length >= 2) {
				tree = tree.valueOf(args[1].toUpperCase());
			}
			
			if (args.length >= 3) {
				size = (float)parseDouble(args[2]);
			}
			
			if (args.length == 4) {
				bend = (float)parseDouble(args[3]);
			}
			
			DynTreeGenerators.initGenerators();
			
			DynTreeGen gen = tree.defaultGenerator;
			if (args[0].equals("genltree")) gen = tree.lushGenerator;
			if (args[0].equals("genptree")) gen = tree.poorGenerator;
			
			gen.growTree(sender.getEntityWorld(), sender.getPosition().down().east(3), size);
		}
		
		
		
		
		
		if (args[0].equals("genlayer")) {
			if (args.length == 1) {
				sender.addChatMessage(new ChatComponentText(getCommandUsage(sender)));
				return;
			}
			
			long seed = sender.getEntityWorld().rand.nextInt(50000);
			
			//seed = 1L;
			
			GenLayerVC.shouldDraw = true;
			
			if (args[1].equals("climate")) {	
				GenLayerVC.genClimate(seed);
			}
			if (args[1].equals("forest")) {	
				GenLayerVC.genForest(seed);
			}
			if (args[1].equals("biomes")) {	
				GenLayerVC.genErosion(seed);
			}
			if (args[1].equals("deposits")) {
				GenLayerVC.genDeposits(seed);
			}
			if (args[1].equals("age")) {
				GenLayerVC.genAgemap(seed);
			}
			if (args[1].equals("heightmap")) {	
				GenLayerVC.genHeightmap(seed);
			}
			if (args[1].equals("noisemod")) {
				GenLayerVC.genNoiseFieldModifier(seed);
			}
			
			
			GenLayerVC.shouldDraw = false;
			

			sender.addChatMessage(new ChatComponentText("Layers generated with seed " + seed + " to " + getCleanPath()));
			
		}

		
		if (args[0].equals("climate")) {
			int climate[] = VCraftWorld.instance.getClimate(sender.getPosition());
			int forest = VCraftWorld.instance.getForest(sender.getPosition());
			
			sender.addChatMessage(new ChatComponentText(
				"Temperature " + climate[0] + 
				", Rainfall " + climate[2] + 
				", Fertility " + climate[1] + 
				", Forest " + forest + 
				", mod forest " + EnumTree.getForestDensity(forest, climate[2], climate[0]) + 
				", descaled temp " + VCraftWorld.instance.deScaleTemperature(climate[0])
			));
			
			EnumFlowerGroup flora = EnumFlowerGroup.getRandomFlowerForClimate(climate[2], climate[0], forest, sender.getEntityWorld().rand);
			System.out.println("chosen flower " + flora);
			
			//EnumTree tree = EnumTree.getRandomTreeForClimate(climate[2], climate[0], forest, sender.getPosition().getY(), sender.getEntityWorld().rand);
			//System.out.println("chosen tree " + tree);
			/*if (flora != null) {
				sender.getEntityWorld().setBlockState(sender.getPosition(), flora.variants[0].getBlockState());
			}*/
			//System.out.println("tree size " + (0.66f - Math.max(0, (sender.getPosition().getY() * 1f / EnumTree.SCOTSPINE.maxy) - 0.5f)));   //sender.getEntityWorld().rand.nextFloat()/3 - 
		}
		
		if (args[0].equals("reloadgrass")) {
			VCraftWorld.instance.loadGrassColors(Minecraft.getMinecraft().getResourceManager());
			sender.addChatMessage(new ChatComponentText("reloaded."));
		}
		
		if (args[0].equals("grasscolor")) {
			sender.addChatMessage(new ChatComponentText("#" + Integer.toHexString(VCraftWorld.instance.getGrassColorAtPos(sender.getPosition()))));
		}
		
		if (args[0].equals("toplayers")) {
			EnumRockType rocktype = EnumRockType.CHERT;
			
			IBlockState[]states = EnumCrustLayerGroup.getTopLayers(rocktype, sender.getPosition().down(), sender.getEntityWorld().rand);
			
			System.out.println(states.length);
			
			for (IBlockState state : states) {
				System.out.println(state);
			}
		}
		
		if (args[0].equals("noisegen")) {
			BlockPos pos = sender.getPosition();
			NoiseGeneratorOctaves noiseGen1 = new NoiseGeneratorOctaves(sender.getEntityWorld().rand, 4);
			int xSize = 5;
			int ySize = 17;
			int zSize = 5;
			
			double horizontalScale = 300D;
			double verticalScale = 300D;      // probably horizontal scale
			
			double noise1[] = new double[0];
			noise1 = noiseGen1.generateNoiseOctaves(null, pos.getX(), 0, pos.getZ(), xSize, ySize, zSize, horizontalScale, verticalScale, horizontalScale);
			
			//File outFile = new File(name+".bmp");
			
			
			for (double num : noise1) {
				System.out.println(num);
			}
			
		}
		
	}
	
	
	public static String getCleanPath() {
	   File sdf = new File("");
	   return sdf.getAbsolutePath();
	}

}
