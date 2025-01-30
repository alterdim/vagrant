package alternis.vagrant.renderer;

import alternis.vagrant.entity.Abomination;
import alternis.vagrant.model.AbominationModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AbominationRenderer extends GeoEntityRenderer<Abomination> {
    public AbominationRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new AbominationModel());
        this.shadowRadius = 1f;
    }
}
