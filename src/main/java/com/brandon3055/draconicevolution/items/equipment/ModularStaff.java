package com.brandon3055.draconicevolution.items.equipment;

import com.brandon3055.brandonscore.api.TechLevel;
import com.brandon3055.brandonscore.lib.TechPropBuilder;
import com.brandon3055.draconicevolution.api.IReaperItem;
import com.brandon3055.draconicevolution.api.capability.DECapabilities;
import com.brandon3055.draconicevolution.api.modules.ModuleCategory;
import com.brandon3055.draconicevolution.api.modules.lib.ModularOPStorage;
import com.brandon3055.draconicevolution.api.modules.lib.ModuleHostImpl;
import com.brandon3055.draconicevolution.init.EquipCfg;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.FlyingNodeProcessor;
import net.minecraft.pathfinding.NodeProcessor;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.Region;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.brandon3055.draconicevolution.init.ModuleCfg.*;

/**
 * Created by brandon3055 on 21/5/20.
 */
public class ModularStaff extends ToolItem implements IReaperItem, IModularMiningTool, IModularMelee {
    private final TechLevel techLevel;
    private final DEItemTier itemTier;

    public ModularStaff(TechPropBuilder props) {
        //noinspection unchecked
        super(0, 0, new DEItemTier(props, EquipCfg::getStaffDmgMult, EquipCfg::getStaffSpeedMult, EquipCfg::getStaffEffMult), Collections.EMPTY_SET, props.staffProps());
        this.techLevel = props.techLevel;
        this.itemTier = (DEItemTier) getTier();
    }

    @Override
    public TechLevel getTechLevel() {
        return techLevel;
    }

    @Override
    public DEItemTier getItemTier() {
        return itemTier;
    }

    @Override
    public ModuleHostImpl createHost(ItemStack stack) {
        ModuleHostImpl host = new ModuleHostImpl(techLevel, staffWidth(techLevel), staffHeight(techLevel), "staff", removeInvalidModules);
        host.addCategories(ModuleCategory.RANGED_WEAPON);
        return host;
    }

    @Nullable
    @Override
    public ModularOPStorage createOPStorage(ItemStack stack, ModuleHostImpl host) {
        return new ModularOPStorage(host, EquipCfg.getBaseStaffEnergy(techLevel), EquipCfg.getBaseStaffTransfer(techLevel));
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return IModularMiningTool.super.getDestroySpeed(stack, state);
    }

    @Override
    public boolean canHarvestBlock(BlockState blockIn) {
        return true;
    }

    @Override
    public float getBaseEfficiency() {
        return getTier().getEfficiency();
    }

    @Override
    public boolean overrideEffectivity(Material material) {
        return true;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        addModularItemInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public int getReaperLevel(ItemStack stack) {
        return techLevel.index + 1;
    }

//    @Override
//    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
//        if (entity instanceof PlayerEntity) {
//            ((PlayerEntity) entity).ticksSinceLastSwing = (int) Math.ceil(((PlayerEntity) entity).getCooledAttackStrength(0));
//        }
//        return true;
//    }
//
//    @Override
//    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
//        return slotChanged;
//    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity entity, Hand hand) {
//        if (world.isRemote) {
//            NodeProcessor nodeProcessor = new FlyingNodeProcessor();
//            PathFinder pathFinder = new PathFinder(nodeProcessor, 100);
//
//            BlockPos start = new BlockPos(entity);
//            BlockPos target = new BlockPos(entity).add(0, 0, 100);
//
//
//            Region region = new Region(world, start.add(-1, -1, -1), start.add(1, 1, 1));
//
//            pathFinder.func_227478_a_(region, entity, ImmutableSet.of(target), 50, p_225464_4_, this.field_226334_s_);
//
//
//        }


        return super.onItemRightClick(world, entity, hand);
    }
}