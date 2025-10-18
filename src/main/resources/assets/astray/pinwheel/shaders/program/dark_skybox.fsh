#include veil:space_helper

uniform sampler2D DiffuseSampler0;
uniform sampler2D DiffuseDepthSampler;
uniform samplerCube Skybox;
uniform float GameTime;

in vec2 texCoord;
out vec4 fragColor;

float nightFactor(float time) {
    float t = mod(time, 24000.0);
    return clamp(smoothstep(13000.0, 14000.0, t) * (1.0 - smoothstep(23000.0, 24000.0, t)), 0.0, 1.0);
}

void main() {
    float depthSample = texture(DiffuseDepthSampler, texCoord).r;

    if (depthSample >= 1.0) {
        vec3 mcSky = texture(DiffuseSampler0, texCoord).rgb;

        vec3 dir = viewDirFromUv(texCoord);
        vec3 nightSky = texture(Skybox, vec3(-dir.x, dir.y, dir.z)).rgb;

        float factor = nightFactor(GameTime);
        fragColor = vec4(mix(mcSky, nightSky, factor), 1.0);
    } else {
        fragColor = texture(DiffuseSampler0, texCoord);
    }
}
