package alternis.vagrant.renderer;

import alternis.vagrant.entity.RiftEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

@Environment(EnvType.CLIENT)
public class RiftEntityRenderer extends EntityRenderer<RiftEntity> {
    private static final Identifier TEXTURE = new Identifier("vagrant", "textures/entity/rift.png");
    private static final RenderLayer RENDER_LAYER = RenderLayer.getEntityTranslucent(TEXTURE);

    public RiftEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public void render(RiftEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();

        // Center the entity and face the player
        //matrices.multiply(this.dispatcher.getRotation());
        //matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F));

        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(entity.getYaw()));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(entity.getPitch()));

        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0F));

        // Animate over time
        float time = (entity.age + tickDelta) * 0.05f;

        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RENDER_LAYER);
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        Matrix3f normalMatrix = matrices.peek().getNormalMatrix();

        // Render 3 layers for swirling effect
        for (int layer = 0; layer < 3; layer++) {
            matrices.push();
            float layerScale = 1.0f - layer * 0.1f; // Slightly shrink each layer
            matrices.scale(layerScale, layerScale, layerScale);

            // Rotate each layer at different speeds
            float rotation = (time + layer * 20f) * 45f;
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(rotation));

            // Build diamond vertices (counter-clockwise order)
            float size = 1f;
            float uMin = (time % 1.0f); // Animate UVs
            float uMax = uMin + 1.0f;

            // Front face (diamond shape)
            addVertex(vertexConsumer, matrix, normalMatrix, -size, 0, -size, uMin, uMin, light);
            addVertex(vertexConsumer, matrix, normalMatrix, size, 0, -size, uMax, uMin, light);
            addVertex(vertexConsumer, matrix, normalMatrix, size, 0, size, uMax, uMax, light);
            addVertex(vertexConsumer, matrix, normalMatrix, -size, 0, size, uMin, uMax, light);

            matrices.pop();
        }

        matrices.pop();
    }

    private void addVertex(VertexConsumer consumer, Matrix4f matrix, Matrix3f normalMatrix, float x, float y, float z, float u, float v, int light) {
        consumer.vertex(matrix, x, y, z)
                .color(255, 255, 255, 200)
                .texture(u, v)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(light)
                .normal(normalMatrix, 0, 1, 0)
                .next();
    }


    @Override
    public Identifier getTexture(RiftEntity entity) {
        return TEXTURE;
    }
}