package alternis.vagrant.model;

import alternis.vagrant.entity.Abomination;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

import static alternis.vagrant.Vagrant.MOD_ID;

@Environment(EnvType.CLIENT)
public class AbominationModel extends GeoModel<Abomination> {

    @Override
    @SuppressWarnings("removal")
    public Identifier getModelResource(Abomination abomination) {
        return new Identifier(MOD_ID, "geo/entity/abomination.geo.json");
    }

    @Override
    @SuppressWarnings("removal")
    public Identifier getTextureResource(Abomination abomination) {
        return new Identifier(MOD_ID, "textures/entity/abomination.png");
    }

    @Override
    public Identifier getAnimationResource(Abomination abomination) {
        return new Identifier(MOD_ID, "animations/abomination.json");
    }
}
