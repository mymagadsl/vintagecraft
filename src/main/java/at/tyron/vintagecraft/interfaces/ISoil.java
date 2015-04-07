package at.tyron.vintagecraft.Interfaces;

import at.tyron.vintagecraft.BlockClass.TreeClass;
import at.tyron.vintagecraft.WorldProperties.Terrain.EnumFertility;
import at.tyron.vintagecraft.WorldProperties.Terrain.EnumOrganicLayer;
import at.tyron.vintagecraft.WorldProperties.Terrain.EnumTree;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public interface ISoil {

	
	boolean canSpreadGrass(World world, BlockPos pos);
	boolean canGrowTree(World world, BlockPos pos, EnumTree tree);
	boolean canGrowGrass(World world, BlockPos pos);
	boolean canGrowTallGrass(World world, BlockPos pos);
	
	EnumFertility getFertility(World world, BlockPos pos);
	
	IProperty getOrganicLayerProperty(World world, BlockPos pos);
}
