package com.tamashenning.lotiscraft.common.tileentities.washers;

import com.tamashenning.lotiscraft.common.inventory.InternalInventory;
import com.tamashenning.lotiscraft.common.inventory.InventoryOperation;
import com.tamashenning.lotiscraft.common.tileentities.TileEntityInventoryBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.*;

/**
 * Created by ironr on 6/18/2016.
 */
public class TileEntityIronWasher extends TileEntityInventoryBase implements IFluidHandler {
    private InternalInventory internalInventory = new InternalInventory(this, 10);

    FluidTank tank = new FluidTank(4000);

    @Override
    public IInventory getInternalInventory() {
        return internalInventory;
    }

    @Override
    public void onChangeInventory(IInventory inv, int slot, InventoryOperation operation, ItemStack removed, ItemStack added) {

    }

    @Override
    public int[] getAccessibleSlotsBySide(EnumFacing side) {
        return new int[0];
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return null;
    }

    @Override
    public boolean canBeRotated() {
        return true;
    }

    @Override
    public int fill(EnumFacing from, FluidStack resource, boolean doFill)
    {
        int filled = tank.fill(resource, doFill);
        if(doFill && filled > 0) {
            IBlockState state = worldObj.getBlockState(pos);
            worldObj.notifyBlockUpdate(pos, state, state, 8); // TODO check flag
        }
        return filled;
    }

    @Override
    public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain)
    {
        // not used in this test
        return null;
    }

    @Override
    public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain)
    {
        FluidStack drained = tank.drain(maxDrain, doDrain);
        if(doDrain && drained != null) {
            IBlockState state = worldObj.getBlockState(pos);
            worldObj.notifyBlockUpdate(pos, state, state, 8); // TODO check flag
        }
        return drained;
    }

    @Override
    public boolean canFill(EnumFacing from, Fluid fluid)
    {
        return tank.getFluidAmount() == 0 ||
                (tank.getFluid().getFluid() == fluid && tank.getFluidAmount() < tank.getCapacity());
    }

    @Override
    public boolean canDrain(EnumFacing from, Fluid fluid)
    {
        return tank.getFluidAmount() > 0;
    }

    @Override
    public FluidTankInfo[] getTankInfo(EnumFacing from)
    {
        return new FluidTankInfo[]{new FluidTankInfo(tank)};
    }

    @Override
    public void readFromNBT(NBTTagCompound tags)
    {
        super.readFromNBT(tags);
        tank.readFromNBT(tags);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tags)
    {
        tags = super.writeToNBT(tags);
        tank.writeToNBT(tags);
        return tags;
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound tag = new NBTTagCompound();
        tag = writeToNBT(tag);
        return new SPacketUpdateTileEntity(this.getPos(), this.getBlockMetadata(), tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        readFromNBT(pkt.getNbtCompound());
    }

}

