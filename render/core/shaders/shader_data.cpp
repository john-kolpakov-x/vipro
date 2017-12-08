#include "shader_data.h"
#include "tri.frag.spv.hpp"
#include "tri.vert.spv.hpp"

ShaderData getTriVertShaderData() {
  ShaderData ret = {};
  ret.len = TRI_VERT_SPV_HPP_LEN;
  ret.data = TRI_VERT_SPV_HPP_DATA;
  return ret;
}

ShaderData getTriFragShaderData() {
  ShaderData ret = {};
  ret.len = TRI_FRAG_SPV_HPP_LEN;
  ret.data = TRI_FRAG_SPV_HPP_DATA;
  return ret;
}
