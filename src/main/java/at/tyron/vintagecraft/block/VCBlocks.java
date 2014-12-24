package at.tyron.vintagecraft.block;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import at.tyron.vintagecraft.ModInfo;
import at.tyron.vintagecraft.VintageCraftConfig;
import at.tyron.vintagecraft.TileEntity.TEOre;
import at.tyron.vintagecraft.World.EnumMaterialDeposit;
import at.tyron.vintagecraft.World.EnumOrganicLayer;
import at.tyron.vintagecraft.World.EnumRockType;
import at.tyron.vintagecraft.block.*;
import at.tyron.vintagecraft.item.ItemBrick;
import at.tyron.vintagecraft.item.ItemOre;
import at.tyron.vintagecraft.item.ItemTopSoil;
import at.tyron.vintagecraft.item.ItemRock;

public class VCBlocks {
	public static VCBlock uppermantle;
	
	public static VCBlock rock;
	public static VCBlock bedrock;
	public static VCBlock brick;
	//public static VCBlock soil;

	public static VCBlock regolith;
	public static VCBlock subsoil;
	public static VCBlock topsoil;

	// Todo
	public static VCBlock charredtopsoil;  // Burned dirt when in contact with lava 
	public static VCBlock lichen; // Mossy stuff that grows on stones
	
	
	// Deposits
	public static BlockOreVC rawore;
	public static String raworeName = "rawore";
	public static ModelResourceLocation oremodelLocation = new ModelResourceLocation(ModInfo.ModID + ":" + raworeName, null);
	
	public static VCBlock rawclay;
	public static VCBlock peat;
	public static VCBlock lignite;
	public static VCBlock coal;
	

	
	public static void init() {
		initBlocks();
		initHardness();
		initTileEntities();
	}
	
	
	public static void initBlocks() {
		topsoil = new BlockTopSoil().setHardness(2F).registerMultiState("topsoil", ItemTopSoil.class, "topsoil", EnumOrganicLayer.values()).setStepSound(Block.soundTypeGrass);
		rawclay = new BlockRawClay().setHardness(2F).registerSingleState("rawclay", ItemBlock.class).setStepSound(Block.soundTypeGrass);
		peat = new BlockPeat().setHardness(2F).registerMultiState("peat", ItemBlock.class, "peat", EnumOrganicLayer.values()).setStepSound(Block.soundTypeGrass);
		
		
		subsoil = new BlockSubSoil().setHardness(2F).registerMultiState("subsoil", ItemRock.class, "subsoil", EnumRockType.values()).setStepSound(Block.soundTypeGravel);
		regolith = new BlockSubSoil().setHardness(2F).registerMultiState("regolith", ItemRock.class, "regolith", EnumRockType.values()).setStepSound(Block.soundTypeGravel);
		rock = new BlockRock().setHardness(2F).registerMultiState("rock", ItemRock.class, "rock", EnumRockType.values());
		
		uppermantle = new BlockUpperMantle().registerSingleState("uppermantle", ItemBlock.class).setBlockUnbreakable().setResistance(6000000.0F);
	}
	
	
	public static void initTileEntities() {
		rawore = new BlockOreVC();
		rawore.setUnlocalizedName(ModInfo.ModID + ":" + raworeName);
		rawore.setHardness(2F);
		
		EnumMaterialDeposit.NATIVEGOLD.block = rawore;
		EnumMaterialDeposit.LIMONITE.block = rawore;
		EnumMaterialDeposit.LIGNITE.block = rawore;
		EnumMaterialDeposit.BITUMINOUSCOAL.block = rawore;
		EnumMaterialDeposit.NATIVECOPPER.block = rawore;
		
		GameRegistry.registerBlock(rawore, raworeName); //.registerSingleState("ore", ItemOre.class);	
		GameRegistry.registerTileEntity(TEOre.class, ModInfo.ModID + ":orete");
	}






	private static void initHardness() {
		rock.setHarvestLevel("pickaxe", 0);
		
		topsoil.setHarvestLevel("shovel", 0);
		subsoil.setHarvestLevel("shovel", 0);
		rawclay.setHarvestLevel("shovel", 0);
		
		regolith.setHarvestLevel("shovel", 1);
		peat.setHarvestLevel("shovel", 1);
		
		
		
		
	}
	

}