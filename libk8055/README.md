K8055 Linux driver
------

 - Compile a driver version for ia32


Instruction
----
Here is how to build your own driver from the source code:

 - Install libusb-0.1.x and libusb-dev wih your favorite package manager. 
   Warning: Do not install the new version of the libusb 1.x ! 
   $apt-get install libusb-0.1.6
 - Install build-essential and gcc
   $apt-get install build-essential
 - Go inside src/
 - Run 'make' command
 - Run 'make' install to install the driver and a tools to manually interact with the card: k8055
 - Connect the card 
 - Run 'dmesg' to check if the card was detected
 - Run test by executing a command such as root
   $su
   >> enter password
   $k8055 -d:1 
   >> led number 1 should be on
   $k8055 -help 
   >> Show more information about the tools
 - You are ready to move the java part.
 

