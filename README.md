# webcrawler
It is a scalable, event driven, configurable web crawler

Here are the basic features of the crawler:
1. It consists of 4 services: Crawler, Parser, SiteMap Generator, Output Response generator
2. Each service is independent of the other one and the crawler is completely event driven
3. Everything is configurable from starting service to stopping service, choosing time outs, choosing random user agents

4. It facilitates to chose the depth of crawling, and stops once the depth is reached

5. It also have other modules like transformation and filters for filtering and cleaning the web urls.

 6. This system is scalable at each service level and number of threads can be configured from config for each service
