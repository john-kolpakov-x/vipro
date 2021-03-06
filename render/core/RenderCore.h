#ifndef VIPRO_RENDER_CORE_RENDER_CORE_H
#define VIPRO_RENDER_CORE_RENDER_CORE_H

#include "use_glfw3.h"
#include <vector>
#include "VDeleter.h"
#include "vk_util.h"
#include "options.h"
#include "ImageData.h"

class RenderCore {
public:
  RenderCore();

  ~RenderCore();

  int getWidth();

  int getHeight();

  void initialize();

  void mainLoop();

  void onResize(int newWidth, int newHeight);

  ImageData textureImageData;

private:
  int width, height;

  GLFWwindow *window;
  VDeleter<VkInstance> instance{vkDestroyInstance};
  VDeleter<VkDebugReportCallbackEXT> callback{instance, destroyDebugReportCallbackEXT};
  VDeleter<VkSurfaceKHR> surface{instance, vkDestroySurfaceKHR};
  VkPhysicalDevice physicalDevice = VK_NULL_HANDLE;
  QueueFamilyIndices queueFamilyIndices;
  VkQueue presentQueue;
  VkQueue graphicsQueue;
  VDeleter<VkDevice> device{vkDestroyDevice};
  VDeleter<VkSwapchainKHR> swapChain{device, vkDestroySwapchainKHR};
  std::vector<VkImage> swapChainImages;
  VkFormat swapChainImageFormat;
  VkExtent2D swapChainExtent;
  std::vector<VDeleter<VkImageView>> swapChainImageViews;
  VDeleter<VkDescriptorSetLayout> descriptorSetLayout{device, vkDestroyDescriptorSetLayout};
  VDeleter<VkPipelineLayout> pipelineLayout{device, vkDestroyPipelineLayout};
  VDeleter<VkRenderPass> renderPass{device, vkDestroyRenderPass};
  VDeleter<VkPipeline> graphicsPipeline{device, vkDestroyPipeline};
  std::vector<VDeleter<VkFramebuffer>> swapChainFrameBuffers;
  VDeleter<VkCommandPool> commandPool{device, vkDestroyCommandPool};

  VDeleter<VkImage> textureImage{device, vkDestroyImage};
  VDeleter<VkDeviceMemory> textureImageMemory{device, vkFreeMemory};
  VDeleter<VkImageView> textureImageView{device, vkDestroyImageView};
  VDeleter<VkSampler> textureSampler{device, vkDestroySampler};


  VDeleter<VkImage> depthImage{device, vkDestroyImage};
  VDeleter<VkDeviceMemory> depthImageMemory{device, vkFreeMemory};
  VDeleter<VkImageView> depthImageView{device, vkDestroyImageView};

  VDeleter<VkBuffer> vertexBuffer{device, vkDestroyBuffer};
  VDeleter<VkDeviceMemory> vertexBufferMemory{device, vkFreeMemory};

  VDeleter<VkBuffer> indexBuffer{device, vkDestroyBuffer};
  VDeleter<VkDeviceMemory> indexBufferMemory{device, vkFreeMemory};

  VDeleter<VkBuffer> uniformStagingBuffer{device, vkDestroyBuffer};
  VDeleter<VkDeviceMemory> uniformStagingBufferMemory{device, vkFreeMemory};
  VDeleter<VkBuffer> uniformBuffer{device, vkDestroyBuffer};
  VDeleter<VkDeviceMemory> uniformBufferMemory{device, vkFreeMemory};

  VDeleter<VkDescriptorPool> descriptorPool{device, vkDestroyDescriptorPool};
  VkDescriptorSet descriptorSet;

  std::vector<VkCommandBuffer> commandBuffers;

  VDeleter<VkSemaphore> imageAvailableSemaphore{device, vkDestroySemaphore};
  VDeleter<VkSemaphore> renderFinishedSemaphore{device, vkDestroySemaphore};

  void initWindow();

  void initVulkan();

  void initVulkan_createInstance();

  void initVulkan_setupDebugCallback();

  void initVulkan_createSurface();

  void initVulkan_selectPhysicalDevice();

  void initVulkan_createLogicalDevice();

  void initVulkan_createSwapChain();

  void initVulkan_createImageViews();

  void initVulkan_createRenderPass();

  void initVulkan_createGraphicsPipeline();

  void initVulkan_createFrameBuffers();

  void initVulkan_createCommandPool();

  void initVulkan_createDepthResources();

  void initVulkan_createVertexBuffer();

  void initVulkan_createCommandBuffers();

  void initVulkan_createSemaphores();

  void initVulkan_createDescriptorSetLayout();

  void initVulkan_createIndexBuffer();

  void initVulkan_createUniformBuffer();

  void initVulkan_createDescriptorPool();

  void initVulkan_createDescriptorSet();

  void updateUniformBuffer();

  void drawFrame();

  void recreateSwapChain();

  QueueFamilyIndices findQueueFamilyIndicesIn(VkPhysicalDevice aPhysicalDevice);

  bool isDeviceSuitable(VkPhysicalDevice aPhysicalDevice, int deviceIndex);

  bool checkValidationLayerSupport();

  std::vector<const char *> getRequiredExtensions();

  void checkExtensions(std::vector<const char *> requiredExtensions);

  friend VKAPI_ATTR VkBool32 VKAPI_CALL debugCallbackStatic( // NOLINT
      VkDebugReportFlagsEXT flags, VkDebugReportObjectTypeEXT objType,
      uint64_t obj, size_t location, int32_t code,
      const char *layerPrefix, const char *msg, void *userData);

  bool debugCallback(
      VkDebugReportFlagsEXT flags, VkDebugReportObjectTypeEXT objType,
      uint64_t obj, size_t location, int32_t code,
      const char *layerPrefix, const char *errorMessage);

  bool checkDeviceExtensionSupport(VkPhysicalDevice aPhysicalDevice);

  void createShaderModule(ShaderCode shaderCode, VDeleter<VkShaderModule> &shaderModule, const std::string &name);

  void searchMemoryTypeIndex(bool *found, uint32_t *index, uint32_t typeFilter, VkMemoryPropertyFlags properties);

  uint32_t findMemoryTypeIndex(uint32_t typeFilter, VkMemoryPropertyFlags properties);

  void createBuffer(VkDeviceSize size, VkBufferUsageFlags usage, VkMemoryPropertyFlags properties,
                    VDeleter<VkBuffer> &buffer, VDeleter<VkDeviceMemory> &bufferMemory);

  void copyBuffer(VkBuffer srcBuffer, VkBuffer dstBuffer, VkDeviceSize size);

  VkFormat
  findSupportedFormat(const std::vector<VkFormat> &candidates, VkImageTiling tiling, VkFormatFeatureFlags features);

  VkFormat findDepthFormat();

  void createImage(uint32_t width, uint32_t height, VkFormat format, VkImageTiling tiling, VkImageUsageFlags usage,
                   VkMemoryPropertyFlags properties, VDeleter<VkImage> &image, VDeleter<VkDeviceMemory> &imageMemory);

  void
  createImageView(VkImage image, VkFormat format, VkImageAspectFlags aspectFlags, VDeleter<VkImageView> &imageView);

  void transitionImageLayout(VkImage image, VkFormat format, VkImageLayout oldLayout, VkImageLayout newLayout);

  VkCommandBuffer beginSingleTimeCommands();

  void endSingleTimeCommands(VkCommandBuffer commandBuffer);

  void initVulkan_createTextureImage();

  void copyImage(VkImage srcImage, VkImage dstImage, uint32_t width, uint32_t height);

  void initVulkan_createTextureImageView();

  void initVulkan_createTextureSampler();
};

#endif //VIPRO_RENDER_CORE_RENDER_CORE_H
