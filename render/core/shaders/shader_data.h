#ifndef VIPRO_RENDER_CORE_SHADER_CODE_H
#define VIPRO_RENDER_CORE_SHADER_CODE_H

typedef struct ShaderCode {
  unsigned int len;
  const unsigned char *data;
} ShaderCode;

ShaderCode getTriVertShaderCode();

ShaderCode getTriFragShaderCode();

#endif //VIPRO_RENDER_CORE_SHADER_CODE_H
