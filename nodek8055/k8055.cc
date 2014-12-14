#include <node.h>
using namespace node;

#include <v8.h>
using namespace v8;

//#include <iostring>
#include <string>
//using namespace std;

#include <usb.h>
#include "k8055.h"

#define STR_BUFF 256
#define false 0
#define true 1

extern int DEBUG;

bool isConnected = false;

Handle<Value> Connect(const Arguments& args) {
  HandleScope scope;

  if (args.Length() != 1) {
    ThrowException(Exception::TypeError(String::New("Expect card number")));
    return scope.Close(Undefined());
  }

//  usb_set_debug(2);

  int i = args[0]->Uint32Value();
  bool ack = (OpenDevice(i) == 0);
  isConnected = ack;
  return scope.Close(v8::Boolean::New(ack));
}

Handle<Value> SetDigital(const Arguments& args) {
  HandleScope scope;

  if (args.Length() != 1) {
    ThrowException(Exception::TypeError(String::New("Expect integer between 0 and 255")));
    return scope.Close(Undefined());
  }

  int i = args[0]->Uint32Value();

  bool ack = false;
  if(isConnected)
  {
    SetAllValues(i,0,0);
    ack = true;
  }
  return scope.Close(v8::Boolean::New(ack));
}

Handle<Value> Disconnect(const Arguments& args) {
  HandleScope scope;

  if (args.Length() != 0) {
    ThrowException(Exception::TypeError(String::New("No argument expected")));
    return scope.Close(Undefined());
  }

  bool ack = false;
  if(isConnected)
  {
    CloseDevice();
    ack = true;
  }
  return scope.Close(v8::Boolean::New(ack));
}

Handle<Value> GetDigital(const Arguments& args) {
  HandleScope scope;

  if (args.Length() != 0) {
    ThrowException(Exception::TypeError(String::New("No argument expected")));
    return scope.Close(Undefined());
  }

  long d = -1;
  if(isConnected)
  {
    d = ReadAllDigital();
  }
  return scope.Close(v8::Number::New(d));
}


void init(Handle<Object> exports) {

  exports->Set(String::NewSymbol("connect"),
      FunctionTemplate::New(Connect)->GetFunction());

  exports->Set(String::NewSymbol("setDigital"),
      FunctionTemplate::New(SetDigital)->GetFunction());

  exports->Set(String::NewSymbol("getDigital"),
      FunctionTemplate::New(GetDigital)->GetFunction());

  exports->Set(String::NewSymbol("disconnect"),
      FunctionTemplate::New(Disconnect)->GetFunction());
}

NODE_MODULE(k8055, init)

