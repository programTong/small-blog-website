
export default {
    headers: {
        
    },
    params: {},
    mode: 'cors',
    addHeader: function (key, value){
        this.headers[key]=value;
    },
    setMode: function (mode){
        this.mode=mode;
    },
    addParam: function (key, value){
        this.params[key]=value;
    },
    reset: function (){
        this.headers= {
        };
        this.params={};
        this.mode= 'cors';
    },
    post: function (uri, body){
        let url = uri;
        if (this.params.length!==0){
            url+='?';
        }
        for (let key in this.params) {
            url+=key+"="+this.params[key]+"&";
        }
        let response = fetch(url,{
            method: 'post',
            headers: this.headers,
            mode: this.mode,
            body: body
        })
        this.reset();
        return response;

    },
    get: function (uri){
        let url = uri;
        if (this.params.length!==0){
            url+='?';
        }
        for (let key in this.params) {
            url+=key+"="+this.params[key]+"&";
        }
        let response =  fetch(url,{
            method: 'get',
            headers: this.headers,
            mode: this.mode
        });
        this.reset();
        return response;
    }
}
