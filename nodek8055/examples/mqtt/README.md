MQTT and K8055 on Raspberry PI example
====

How TO
-----
1- Install Mosquito 
2- Configure mosquito 
  - Enable user:password <> test:test
3- Copy publisher on a remote host
4- Run 'npm install' to make sure all dependency are available
5- Run consumer on Raspberry Pi
 $sudo node consumer.js
6- Run publisher on remote host
 $node publisher.js
7- See LED getting updated.
