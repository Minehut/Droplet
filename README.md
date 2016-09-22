##Warning: 3rd party servers are currently disabled on Minehut. This project works perfectly. The service was disabled for non-technical reasons.

# Droplet
Software used to integrate third party servers into Minehut.

About
---
The Droplet system was developed for servers that are not hosted on the Minehut platform. In order to maintain the security of Minehut, the Droplet server was designed to provide a bridge between regular minecraft servers and our database. This allows Minehut to add any third party server without having to worry about them decompiling code to find our database info. 

Setup
---
Verified third party servers are given a secret key by a Minehut administrator. Third party server owners then enter this key into the `config.yml` of the Droplet Bukkit plugin. Once the server starts, the Droplet plugin will immediately begin updating status info (online players, max players, etc) to be displayed in Minehut's server list. 
