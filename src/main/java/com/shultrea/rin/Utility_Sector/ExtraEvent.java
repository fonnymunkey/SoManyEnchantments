package com.shultrea.rin.Utility_Sector;

import com.shultrea.rin.Main_Sector.ModConfig;
import com.shultrea.rin.Prop_Sector.ArrowPropertiesProvider;
import com.shultrea.rin.Prop_Sector.IArrowProperties;
import com.shultrea.rin.enchantments.base.EnchantmentBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ExtraEvent {
	
	@SubscribeEvent
	public void onArrowHit(LivingDamageEvent fEvent) {
		if(fEvent.getSource().damageType != "arrow") return;
		if(!(fEvent.getSource().getImmediateSource() instanceof EntityArrow)) return;
		EntityArrow cause = (EntityArrow)fEvent.getSource().getImmediateSource();
		if(!cause.hasCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null)) return;
		if(!EnchantmentBase.isDamageSourceAllowed(fEvent.getSource()))
			return;
		IArrowProperties properties = cause.getCapability(ArrowPropertiesProvider.ARROWPROPERTIES_CAP, null);
		int flameLevel = properties.getFlameLevel();
		switch(flameLevel) {
			case 1:
				if(ModConfig.enabled.lesserFlame) fEvent.getEntityLiving().setFire(2);
				break;
			case 2:
				if(ModConfig.enabled.advancedFlame) fEvent.getEntityLiving().setFire(15);
				break;
			case 3:
				if(ModConfig.enabled.supremeFlame) fEvent.getEntityLiving().setFire(30);
				break;
			default:
				break;
		}
		float pullPower = properties.getPullPower();
		if(pullPower > 0 && ModConfig.enabled.drag) {
			pullPower *= -1;
			float distance = MathHelper.sqrt(cause.motionX * cause.motionX + cause.motionZ * cause.motionZ);
			fEvent.getEntityLiving().addVelocity(cause.motionX * pullPower * 0.6000000238418579D / (double)distance, 0.1D, cause.motionZ * pullPower * 0.6000000238418579D / (double)distance);
			fEvent.getEntityLiving().velocityChanged = true;
		}
	}
}