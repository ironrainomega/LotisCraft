package com.tamashenning.lotiscraft.common.items.misc;

import com.tamashenning.lotiscraft.LotisCraftCreativeTabs;
import com.tamashenning.lotiscraft.common.items.ItemBase;
import com.tamashenning.lotiscraft.common.util.IProvideRecipe;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by ironr on 6/17/2016.
 */
public class ItemFiber extends ItemBase implements IProvideRecipe {

    public ItemFiber() {
        super("misc/fiberitem");
        this.setCreativeTab(LotisCraftCreativeTabs.tabGeneral);
        this.setInternalName("fiber_item");
    }

    @Override
    public void RegisterRecipes() {
        GameRegistry.addShapelessRecipe(new ItemStack(Items.STRING), this, this, this, this);
    }
}
