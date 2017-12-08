
#include "shader_data.h"
#include "tri.frag.spv.hpp"
#include "tri.vert.spv.hpp"

ShaderCode getTriVertShaderCode() {
  ShaderCode ret = {};
  ret.len = TRI_VERT_SPV_HPP_LEN;
  ret.data = TRI_VERT_SPV_HPP_DATA;
  return ret;
}

ShaderCode getTriFragShaderCode() {
  ShaderCode ret = {};
  ret.len = TRI_FRAG_SPV_HPP_LEN;
  ret.data = TRI_FRAG_SPV_HPP_DATA;
  return ret;
}
