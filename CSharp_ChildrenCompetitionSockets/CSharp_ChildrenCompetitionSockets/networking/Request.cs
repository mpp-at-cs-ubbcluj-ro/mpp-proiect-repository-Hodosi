using System;

namespace networking
{
    [Serializable]
    public class Request
    {
        public RequestType type { get; set; }
        public Object data { get; set; }

        private Request(){}
        
        public string toString() {
            return "Request{" +
                   "type='" + type + '\'' +
                   ", data='" + data + '\'' +
                   '}';
        }
        
        public class Builder {
            private Request request = new Request();

            public Builder type(RequestType type) {
                request.type = type;
                return this;
            }

            public Builder data(Object data) {
                request.data = data;
                return this;
            }

            public Request build() {
                return request;
            }
        }
    }
}