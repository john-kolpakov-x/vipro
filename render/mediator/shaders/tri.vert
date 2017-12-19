#version 450
#extension GL_ARB_separate_shader_objects : enable

layout(binding = 0) uniform UniformBufferObject {
    mat4 model;
    mat4 view;
    mat4 projection;
    mat4 projection2;
} ubo;

layout(location = 0) in vec3 inPosition;
layout(location = 1) in vec3 inColor;

layout(location = 0) out vec3 fragColor;

out gl_PerVertex {
    vec4 gl_Position;
};

void main() {
//    gl_Position = ubo.proj * ubo.view * ubo.model * vec4(inPosition, 1.0);
//    gl_Position = vec4(inPosition, 1.0);

    if (2 <= inColor.r && inColor.r <= 3) {
      gl_Position = ubo.projection2 * vec4(inPosition, 1.0);
      fragColor = inColor - 2;
    } else {
      gl_Position = ubo.projection * ubo.view * ubo.model * vec4(inPosition, 1.0);
      fragColor = inColor;
    }
}
