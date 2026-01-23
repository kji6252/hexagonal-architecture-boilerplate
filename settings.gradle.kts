rootProject.name = "boilerplate-service"

include("core")
include("boilerplate-service-api")
include("port-in")
include("port-out")
include("application")
include("domain")
include(
  "adapter:web-adapter",
  "adapter:persistence-adapter",
  "adapter:client-adapter",
  "adapter:cache-adapter",
  "adapter:memory-adapter",
  "adapter:message-adapter",
  "adapter:stream-adapter",
  "adapter:storage-adapter",
)
include("boilerplate-service-batch")
