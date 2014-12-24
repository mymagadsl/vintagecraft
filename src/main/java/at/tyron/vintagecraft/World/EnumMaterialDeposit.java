package at.tyron.vintagecraft.World;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.IStringSerializable;
import at.tyron.vintagecraft.WorldGen.VCBiome;
import at.tyron.vintagecraft.block.BlockRegolith;
import at.tyron.vintagecraft.block.BlockRock;
import at.tyron.vintagecraft.block.BlockSubSoil;
import at.tyron.vintagecraft.block.BlockTopSoil;
import at.tyron.vintagecraft.block.VCBlock;
import at.tyron.vintagecraft.block.VCBlocks;

public enum EnumMaterialDeposit implements IStringSerializable, IGenLayerSupplier {
	//						    color  weight hgt vari mind maxd 
	NODEPOSIT (-1, null, false,              0, 5000,   0,  0, 0, null),
	
	CLAY (0, VCBlocks.rawclay, false, 20,   70,   1,  1,    2, new VCBiome[]{VCBiome.plains, VCBiome.beach, VCBiome.river, VCBiome.rollingHills}),
	PEAT (1, VCBlocks.peat, false,  40,   70,   1,  1,    2, new VCBiome[]{VCBiome.plains, VCBiome.rollingHills, VCBiome.Mountains, VCBiome.MountainsEdge}),
	
	
	LIGNITE (2,        VCBlocks.rawore, true, 60,   50,   2,  10,  50, new VCBiome[]{VCBiome.plains, VCBiome.rollingHills, VCBiome.Mountains, VCBiome.MountainsEdge}),
	BITUMINOUSCOAL (3, VCBlocks.rawore, true, 80,   40,   2,  50, 150, new VCBiome[]{VCBiome.rollingHills, VCBiome.Mountains, VCBiome.MountainsEdge}),
	
	NATIVECOPPER (4,   VCBlocks.rawore, true, 100,   45,   2,  4,   40, null),
	
	LIMONITE (5,      VCBlocks.rawore, true, 100,   30,   2,  1,  150, null),
	NATIVEGOLD (6,    VCBlocks.rawore, true, 120,   10,   1,  15, 200, null),

	
	;

	

	public int id;
	public Block block;
	public boolean hasOre;
	int color;
	public int averageHeight;
	public int minDepth;
	public int maxDepth;
	
	//public int rarity; // Used as if !nextInt(rarity)
	public final VCBiome[] biomes;
	public int weight;
	
	private EnumMaterialDeposit(int id, Block block, boolean hasOre, int color, int weight, int averageHeight, int minDepth, int maxDepth, VCBiome[] biomes) {
		this.id = id;
		
		this.weight = weight;
		this.block = block;
		this.hasOre = hasOre;
		this.averageHeight = averageHeight;
		this.minDepth = minDepth;
		this.maxDepth = maxDepth;
		this.biomes = biomes;
		this.color = color;
	}
	
	
	public static EnumMaterialDeposit depositForColor(int color) {
		EnumMaterialDeposit[] deposits = values();
		for (int i = 0; i < deposits.length; i++) {
			if (deposits[i].color == color)
				return deposits[i];
		}
		return null;
	}
	
	
	public boolean isParentMaterial(IBlockState state) {
		if (this == CLAY || this == PEAT) return state.getBlock() instanceof BlockTopSoil || state.getBlock() instanceof BlockSubSoil || state.getBlock() instanceof BlockRegolith;
		
		return state.getBlock() instanceof BlockRock && state.getBlock().getMaterial() == Material.rock;
	}

	
	public static EnumMaterialDeposit byId(int id) {
		EnumMaterialDeposit[] deposits = values();
		for (int i = 0; i < deposits.length; i++) {
			if (deposits[i].id == id)
				return deposits[i];
		}
		return null;
		
	}


	
	@Override
	public int getColor() {
		return color;
	}


	@Override
	public int getWeight() {
		return weight;
	}
	
	
	@Override
	public int getDepthMax() {
		return maxDepth;
	}
	
	@Override
	public int getDepthMin() {
		return minDepth;
	}


	@Override
	public String getName() {
		return name().toLowerCase();
	}
	
	
	
    public static String[] getNames() {
    	String[] names = new String[values().length];
    	
    	for (int i = 0; i < values().length; i++) {
    		names[i] = values()[i].name().toLowerCase();
    	}
    	return names;
    }	

        
	
}