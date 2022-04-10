using System;

namespace networking
{
    [Serializable]
    public class Response
    {
        public ResponseType type { get; set; }
        public Object data { get; set; }

        private Response(){}
        
        public string toString(){
            return "Response{" +
                   "type='" + type + '\'' +
                   ", data='" + data + '\'' +
                   '}';
        }


        public class Builder{
            private Response response=new Response();

            public Builder type(ResponseType type) {
                response.type = type;
                return this;
            }

            public Builder data(Object data) {
                response.data = data;
                return this;
            }

            public Response build() {
                return response;
            }
        } 
    }
}