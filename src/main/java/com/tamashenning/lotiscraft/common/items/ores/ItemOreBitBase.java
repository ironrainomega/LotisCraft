package com.tamashenning.lotiscraft.common.items.ores;

import com.tamashenning.lotiscraft.LotisCraftCreativeTabs;
import com.tamashenning.lotiscraft.common.items.ItemBase;
import com.tamashenning.lotiscraft.common.util.IProvideRecipe;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ironr on 6/17/2016.
 */
public abstract class ItemOreBitBase extends ItemBase implements IProvideRecipe {

    private int amountForBlock;
    private Block craftedOre;

    public ItemOreBitBase(String oreName, Block craftedOre) {
        this(oreName, 4, craftedOre);
    }

    public ItemOreBitBase(String oreName, int amountForBlock, Block craftedOre) {
        super("ores/orebit/"+oreName+"item");
        this.setCreativeTab(LotisCraftCreativeTabs.tabOres);
        this.setInternalName("orebit_"+oreName+"_item");
        this.amountForBlock = amountForBlock;
        this.craftedOre = craftedOre;
    }

    @Override
    public void RegisterRecipes() {
        List<ItemStack> recipe = new ArrayList<ItemStack>();
        for (int i=0; i < this.amountForBlock; i++) {
            recipe.add(new ItemStack(this));
        }

        GameRegistry.addRecipe(new ShapelessRecipes(new ItemStack(craftedOre), recipe));
    }
}
