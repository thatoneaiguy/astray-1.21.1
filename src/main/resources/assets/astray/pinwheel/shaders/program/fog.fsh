#include veil:fog
#include veil:space_helper

uniform sampler2D DiffuseSampler0;
uniform sampler2D DiffuseDepthSampler;

uniform vec4 FogColor;
uniform int FogShape;
uniform vec3 pos;

in vec2 texCoord;
out vec4 fragColor;

void main() {
    vec4 baseColor = texture(DiffuseSampler0, texCoord);
    float depth = texture(DiffuseDepthSampler, texCoord).r;

    // Get camera world position
    vec3 cameraWorldPos = (VeilCamera.IViewMat[3]).xyz;

    // Distance from camera to fog center
    float distToPos = distance(cameraWorldPos, pos);

    // Initialize fog factor
    float totalFog = 0.0;

    // Only apply fog if within 15 units
    if (distToPos <= 15.0) {
        float fogFactor = 1.0 - distToPos / 15.0; // closer = more fog

        // Compute scene-space distance for additional depth fog if desired
        vec3 worldPos = screenToWorldSpace(texCoord, depth).xyz;
        float viewDist = length(worldPos - cameraWorldPos);
        float sceneFog = smoothstep(10.0, 100.0, viewDist);

        totalFog = clamp(fogFactor * 1.5 + sceneFog * 0.5, 0.0, 1.0);
    }

    // Tint fog color
    vec4 fogColor = FogColor * vec4(1.0, 0.5, 0.8, 1.0);

    // Apply fog
    fragColor = mix(baseColor, fogColor, totalFog);
}
