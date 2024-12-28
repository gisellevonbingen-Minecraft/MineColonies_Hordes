package test.mixin.common.forge;

import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

@Mixin(value = ConfigValue.class)
public abstract class ConfigValueMixin<T> implements Supplier<T>
{
	@Shadow
	private ForgeConfigSpec spec;

	@Shadow
	public abstract T getDefault();

	@Inject(method = "get", at = @At(value = "HEAD"), cancellable = true)
	private void get_Head(CallbackInfoReturnable<T> cir)
	{
		if (((ForgeConfigSpecAccessor) this.spec).getChildConfig() == null)
		{
			cir.setReturnValue(this.getDefault());
		}

	}

}
