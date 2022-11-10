# Spring Cloud Microservices for the Largest NASA Picture Demo
* **Create** and configure a new Config Service
* **Create** and configure a new Discovery Service
* **Create** a new NASA Proxy service that just proxies calls to this [endpoint](https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&api_key=DEMO_KEY&camera=NAVCAM)
  * Get configs from the Config Service
  * Register to Discovery Service
* **Refactor** your Largest NASA Picture service
  * Get configs from the Config Service
  * Register to Discovery Service
  * Update feign client so it gets the URL from the Discovery Service by name