package utils;
import http.HTTP;
class dns
{
    String webip,webname;
    public dns(String s,String x)
    {
        webname=s;
        webip=x;
    }
    void query()
    {
        HTTP.http(webname) ;
    }
    String show(String s)
    {
        return webname;
    }

}