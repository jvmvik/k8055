var k = require('../k8055').k8055;
k.connect(0);

console.log('Read analog value...');
var v1 = k.getAnalog(1);
console.log('channel 1: ' + v1);

var v2 = k.getAnalog(2);
console.log('channel 2: ' + v2);

k.disconnect();

