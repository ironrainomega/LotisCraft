package com.tamashenning.lotiscraft.common.blocks.washers;

import com.tamashenning.lotiscraft.LotisCraftCreativeTabs;
import com.tamashenning.lotiscraft.common.blocks.BlockTileBase;
import com.tamashenning.lotiscraft.common.inventory.IInventoryHandler;
import com.tamashenning.lotiscraft.common.tileentities.washers.TileEntityIronWasher;
import com.tamashenning.lotiscraft.common.util.InventoryHelper;
import com.tamashenning.lotiscraft.common.util.TileHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.capability.*;
import net.minecraftforge.fluids.capability.wrappers.FluidHandlerWrapper;

/**
 * Created by ironr on 6/18/2016.
 */
public class BlockIronWasher extends BlockTileBase {
    public BlockIronWasher() {
        super(Material.ROCK, "sample/sampleblock");
        this.setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        this.setCreativeTab(LotisCraftCreativeTabs.tabGeneral);
        this.setInternalName("iron_washer");
        this.setTileEntity(TileEntityIronWasher.class);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        TileEntityIronWasher tileEntity = TileHelper.getTileEntity(worldIn, pos, TileEntityIronWasher.class);
        if (tileEntity != null) {
            return state.withProperty(FACING, tileEntity.getForward());
        }
        return state.withProperty(FACING, EnumFacing.NORTH);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (!(te instanceof IFluidHandler)) {
            return false;
        }
        IFluidHandler tank = (IFluidHandler) te;
        side = side.getOpposite();

        // just testing here...
        IInventory inv =  (IInventory)te;
        InventoryHelper.addItemStackToInventory(playerIn.inventory.getStackInSlot(playerIn.inventory.currentItem), inv, 0, 0);

        if (heldItem == null || FluidContainerRegistry.getFluidForFilledItem(heldItem) == null || FluidContainerRegistry.getFluidForFilledItem(heldItem).getFluid() == null ) {
            sendText(playerIn, tank, side);
            return false;
        }

        // do the thing with the tank and the buckets
        if (FluidContainerRegistry.getFluidForFilledItem(heldItem).getFluid() == FluidRegistry.WATER && FluidUtil.interactWithTank(heldItem, playerIn, tank, side)) {
            return true;
        } else {
            sendText(playerIn, tank, side);
        }

        // prevent interaction of the item if it's a fluidcontainer. Prevents placing liquids when interacting with the tank
        return FluidContainerRegistry.isFilledContainer(heldItem) || heldItem.getItem() instanceof IFluidContainerItem;
    }

    private void sendText(EntityPlayer player, IFluidHandler tank, EnumFacing side) {
        if (player.worldObj.isRemote) {
            String text;
            if (tank.getTankInfo(side).length > 0 && tank.getTankInfo(side)[0] != null && tank.getTankInfo(side)[0].fluid != null) {
                text = tank.getTankInfo(side)[0].fluid.amount + "x " + tank.getTankInfo(side)[0].fluid.getLocalizedName();
            } else {
                text = "empty";
            }
            player.addChatMessage(new TextComponentString(text));
        }
    }

    private void sendText(EntityPlayer player, String text) {
        if(player.worldObj.isRemote) {
            player.addChatMessage(new TextComponentString(text));
        }
    }

}
