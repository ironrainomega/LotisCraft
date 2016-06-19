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
public class ItemMesh extends ItemBase implements IProvideRecipe {
    public ItemMesh() {
        super("misc/meshitem");
        this.setCreativeTab(LotisCraftCreativeTabs.tabGeneral);
        this.setInternalName("mesh_item");
    }

    @Override
    public void RegisterRecipes() {
        GameRegistry.addShapelessRecipe(new ItemStack(this), Items.STRING, Items.STRING, Items.STRING, Items.STRING);
    }
}
