#ifndef VIPRO_RENDER_CORE_SHADER_DATA_H
#define VIPRO_RENDER_CORE_SHADER_DATA_H

typedef struct ShaderData {
  unsigned int len;
  const unsigned char *data;
} ShaderData;

ShaderData getTriVertShaderData();

ShaderData getTriFragShaderData();

#endif //VIPRO_RENDER_CORE_SHADER_DATA_H
