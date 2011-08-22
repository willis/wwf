package com.mpaike.util.bot;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
/**
 * <p>Title: Myniko.com</p>
 * <p>Description: Myniko.com</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Myniko.com</p>
 * @author Niko
 * @version 1.0
 */

public class HTTPSocket extends HTTP {

    synchronized public void lowLevelSend(String url,String post)
    throws java.net.UnknownHostException, java.io.IOException {
        String command;// What kind of send POST or GET
        StringBuffer headers;// Used to hold outgoing client headers
        byte buffer[]=new byte[1024];//A buffer for reading
        int l,i;// Counters
        Attribute a;// Used to process incoming attributes
        int port=80;// What port, default to 80
        boolean https = false;// Are we using HTTPS?
        URL u;// Used to help parse the url parameter
        Socket socket = null;
        OutputStream out = null;
        InputStream in = null;

        // parse the URL
        try {
            if ( url.toLowerCase().startsWith("https") ) {
                url = "http" + url.substring(5);// so it can be parsed
                u = new URL(url);
                if ( u.getPort()==-1 )
                    port=443;
                https = true;
            } else
                u = new URL(url);

            if ( u.getPort()!=-1 )
                port = u.getPort();

            // connect
            socket = SocketFactory.getSocket(u.getHost(),port,https);

            socket.setSoTimeout(timeout);
            out = socket.getOutputStream();
            in = socket.getInputStream();

            // send command, i.e. GET or POST
            if ( post == null )
                command = "GET ";
            else
                command = "POST ";

            String file = u.getFile();
            if ( file.length()==0 )
                file="/";

            // proxy support

            if( SocketFactory.useProxy()) {
                addProxyAuthHeader();
                if(port!=80)
                    file = "http://" + u.getHost() + ":" + port + file;
                else
                    file = "http://" + u.getHost() + file;
            }

            // end proxy support

            command = command + file + " HTTP/1.0";
            SocketFactory.writeString(out,command);
            System.out.println("Request: " + command);
            Log.log(Log.LOG_LEVEL_NORMAL,"Request: " + command );

            // Process client headers

            if ( post!=null )
                clientHeaders.set("Content-Length","" + post.length());

            clientHeaders.set("Host", u.getHost() );

            i=0;
            headers = new StringBuffer();
            do {
                a = clientHeaders.get(i++);
                if ( a!=null ) {
                    headers.append(a.getName());
                    headers.append(": ");
                    headers.append(a.getValue());
                    headers.append("\r\n");
                    System.out.println("Client Header:" + a.getName() + "=" + a.getValue());
                    Log.log(Log.LOG_LEVEL_TRACE,"Client Header:" + a.getName() + "=" + a.getValue() );
                }
            } while ( a!=null );
            System.out.println("Writing client headers:" + headers.toString());
            Log.log(Log.LOG_LEVEL_DUMP,"Writing client headers:" + headers.toString() );
            if ( headers.length()>=0 )
                out.write(headers.toString().getBytes() );

            // Send a blank line to signal end of HTTP headers
            SocketFactory.writeString(out,"");
            if ( post!=null ) {
                System.out.println("Socket Post(" + post.length() + " bytes):" + new String(post));
                Log.log(Log.LOG_LEVEL_TRACE,"Socket Post(" + post.length() + " bytes):" + new String(post) );
                out.write( post.getBytes()  );
            }

            /* Read the result */
            /* First read HTTP headers */

            header.setLength(0);
            int chars = 0;
            boolean done = false;

            while ( !done ) {
                int ch;

                ch = in.read();
                if ( ch==-1 )
                    done=true;

                switch ( ch ) {
                    case '\r':
                        break;
                    case '\n':
                        if ( chars==0 )
                            done =true;
                        chars=0;
                        break;
                    default:
                        chars++;
                        break;
                }

                header.append((char)ch);
            }

            // now parse the headers and get content length
            parseHeaders();
            Attribute acl = serverHeaders.get("Content-length");
            int contentLength=0;
            try {
                if ( acl!=null )
                    contentLength = Integer.parseInt(acl.getValue());
            } catch ( Exception e ) {
                System.out.println("Bad value for content-length:"+e);
                Log.logException("Bad value for content-length:",e);
            }

            int max;
            if ( maxBodySize!=-1 )
                max = Math.min(maxBodySize,contentLength );
            else
                max = contentLength;

            if ( max<1 )
                max=-1;

            ByteList byteList = new ByteList();
            byteList.read(in,max);
            body = byteList.detach();
            System.out.println("Socket Page Back:" + new String(body) + "\r\n");
            Log.log(Log.LOG_LEVEL_DUMP,"Socket Page Back:" + new String(body) + "\r\n" );

            if ( (err>=400) && (err<=599) ) {
                System.out.println("HTTP Exception:" + response);
                Log.log(Log.LOG_LEVEL_ERROR,"HTTP Exception:" + response );
                throw new HTTPException(response);
            }

        }

        // Cleanup
        finally {
            if ( out!=null ) {
                try {
                    out.close();
                } catch ( Exception e ) {
                }
            }

            if ( in!=null ) {
                try {
                    in.close();
                } catch ( Exception e ) {
                }
            }

            if ( socket!=null ) {
                try {
                    socket.close();
                } catch ( Exception e ) {
                }
            }
        }
    }

    HTTP copy() {
        return new HTTPSocket();
    }


    /**
     * This method is called to add the user authorization headers
     * to the HTTP request.
     */
    protected void addProxyAuthHeader() {
        if( (SocketFactory.getProxyUID()!=null) && (SocketFactory.getProxyUID().length()>0) ) {
            String hdr = SocketFactory.getProxyUID() + ":" + SocketFactory.getProxyPWD()==null?"":SocketFactory.getProxyPWD();
            String encode = URLUtility.base64Encode(hdr);
            clientHeaders.set("Proxy-Authorization","Basic " + encode );
        }
    }
}
