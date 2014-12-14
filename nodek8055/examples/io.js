/*
 * Read input and copy the value to the output.
 */
var k = require('../k8055').k8055;
k.connect(0);

var counter = 0;
var tmp, d = 0;
setInterval(function()
{
 if(counter++ == 1000) 
 {
   clearInterval();
   k.disconnect();
   console.log("Disconnect");
 }
 else if(counter < 1000)
 {
   d = k.getDigital();
   if(d != tmp)
   { 
    tmp = d;
    console.log("I/O: " + d);
    k.setDigital(d);
   }
 }
}, 200);
