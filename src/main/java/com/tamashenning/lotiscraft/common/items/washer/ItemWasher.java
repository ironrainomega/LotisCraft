package com.tamashenning.lotiscraft.common.items.washer;

import com.tamashenning.lotiscraft.LotisCraftCreativeTabs;
import com.tamashenning.lotiscraft.api.WasherRegistry;
import com.tamashenning.lotiscraft.api.models.Washable;
import com.tamashenning.lotiscraft.api.models.WashableResult;
import com.tamashenning.lotiscraft.common.items.ItemBase;
import com.tamashenning.lotiscraft.common.items.Items;
import com.tamashenning.lotiscraft.common.util.IProvideRecipe;
import com.tamashenning.lotiscraft.common.util.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

import javax.annotation.Nullable;
import java.util.Random;

import static sun.audio.AudioPlayer.player;

/**
 * Created by ironr on 6/17/2016.
 */
public class ItemWasher extends ItemBase implements IProvideRecipe {

    public ItemWasher() {
        super("washer/washeritem");
        this.setCreativeTab(LotisCraftCreativeTabs.tabGeneral);
        this.setInternalName("washer_item");
        this.maxStackSize = 1;
        this.setMaxDamage(128);
    }

    public boolean isWater(EntityPlayer player, World world) {
        RayTraceResult raytraceresult = this.rayTrace(world, player, true);
        // Is it a water block???
        if (raytraceresult == null) {
            return false;
        } else if (raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK) {
            return false;
        } else {
            BlockPos blockpos = raytraceresult.getBlockPos();
            IBlockState iblockstate = world.getBlockState(blockpos);
            Material material = iblockstate.getMaterial();

            if (material == Material.WATER && ((Integer) iblockstate.getValue(BlockLiquid.LEVEL)).intValue() == 0) {
                // Yes it's water...
                return true;
            }
        }

        return false;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 10;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.EAT;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World world, EntityPlayer player, EnumHand hand) {
        if (!this.isWater(player, world))
            return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemStackIn);

        player.setActiveHand(hand);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
    }

    @Nullable
    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {

        if (!worldIn.isRemote) {
            if (entityLiving instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entityLiving;
                ItemStack adjacentItemStack = player.inventory.getStackInSlot(player.inventory.currentItem + 1);
                boolean itemWashed = doLogic(adjacentItemStack, worldIn, player);

                if (!player.isCreative() && itemWashed) {
                    stack.damageItem(1, entityLiving);

                    if (adjacentItemStack.stackSize == 1) {
                        // Ran out of things to wash...
                        player.inventory.removeStackFromSlot(player.inventory.currentItem + 1);
                    } else {
                        // Consume one item
                        player.inventory.decrStackSize(player.inventory.currentItem + 1, 1);
                    }
                }
            }
        }

        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }


    protected boolean doLogic(ItemStack adjacentItemStack, World world, EntityPlayer player) {
        Random random = new Random();

        boolean itemWashed = false;

        for (ItemStack washableStack : WasherRegistry.getBlocks()) {
            if (adjacentItemStack != null && washableStack.isItemEqual(adjacentItemStack)) {
                itemWashed = true;
                Washable w = WasherRegistry.getWashableResult(washableStack);
                for (WashableResult result : w.outWashableResult) {
                    if (!world.isRemote && random.nextFloat() <= result.chance) {
                        BlockPos pos = this.rayTrace(world, player, true).getBlockPos();
                        ItemStack itemToDrop = new ItemStack(result.outItemStack.getItem(), result.outItemStack.stackSize + random.nextInt(result.luckMultiplier + 1));
                        EntityItem entityItem = new EntityItem(world, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, itemToDrop);

                        double f3 = 0.05f;
                        entityItem.motionX = world.rand.nextGaussian() * f3;
                        entityItem.motionY = (0.2d);
                        entityItem.motionZ = world.rand.nextGaussian() * f3;

                        world.spawnEntityInWorld(entityItem);
                    }
                }
            }
        }

        return itemWashed;
    }

    @Override
    public void RegisterRecipes() {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(this),
                "xyx",
                "xyx",
                " x ",
                'x', "plankWood", 'y', Items.MESH_ITEM.getItem()
        ));
    }
}