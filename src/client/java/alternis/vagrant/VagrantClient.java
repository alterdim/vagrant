package alternis.vagrant;

import alternis.vagrant.registry.ModEntities;
import alternis.vagrant.renderer.RiftEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import org.slf4j.LoggerFactory;

import java.util.logging.Logger;

import static alternis.vagrant.Vagrant.MOD_ID;

public class VagrantClient implements ClientModInitializer {
	public static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(ModEntities.RIFT, RiftEntityRenderer::new);
		LOGGER.info("Registered client renderers !");
	}
}