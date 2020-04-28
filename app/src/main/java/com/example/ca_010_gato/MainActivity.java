package com.example.ca_010_gato;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MainActivity extends AppCompatActivity {

    public ImageButton imageButton1,
            imageButton2,
            imageButton3,
            imageButton4,
            imageButton5,
            imageButton6,
            imageButton7,
            imageButton8,
            imageButton9;
    public EditText textPuerto;
    public Button iniciar;

    private DatagramSocket escucha;
    private Thread hilo;
    private String recibido;
    public int turno;
    public int [][] tablero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageButton1 = (ImageButton) findViewById(R.id.imageButton1);
        imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
        imageButton3 = (ImageButton) findViewById(R.id.imageButton3);
        imageButton4 = (ImageButton) findViewById(R.id.imageButton4);
        imageButton5 = (ImageButton) findViewById(R.id.imageButton5);
        imageButton6 = (ImageButton) findViewById(R.id.imageButton6);
        imageButton7 = (ImageButton) findViewById(R.id.imageButton7);
        imageButton8 = (ImageButton) findViewById(R.id.imageButton8);
        imageButton9 = (ImageButton) findViewById(R.id.imageButton9);
        textPuerto= (EditText) findViewById(R.id.editText1);
        iniciar = (Button) findViewById(R.id.button1);

        iniciar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                tablero = new int[3][3];
                tablero [0][0] = -3;
                tablero [0][1] = -3;
                tablero [0][2] = -3;
                tablero [1][0] = -3;
                tablero [1][1] = -3;
                tablero [1][2] = -3;
                tablero [2][0] = -3;
                tablero [2][1] = -3;
                tablero [2][2] = -3;
                turno = 1;
                imageButton1.setImageDrawable(getResources().getDrawable(R.mipmap.ffondo));
                imageButton2.setImageDrawable(getResources().getDrawable(R.mipmap.ffondo));
                imageButton3.setImageDrawable(getResources().getDrawable(R.mipmap.ffondo));
                imageButton4.setImageDrawable(getResources().getDrawable(R.mipmap.ffondo));
                imageButton5.setImageDrawable(getResources().getDrawable(R.mipmap.ffondo));
                imageButton6.setImageDrawable(getResources().getDrawable(R.mipmap.ffondo));
                imageButton7.setImageDrawable(getResources().getDrawable(R.mipmap.ffondo));
                imageButton8.setImageDrawable(getResources().getDrawable(R.mipmap.ffondo));
                imageButton9.setImageDrawable(getResources().getDrawable(R.mipmap.ffondo));
                if(hilo!=null)
                {
                    if(hilo.isAlive())
                    {
                        hilo.interrupt();
                    }
                }
                if(escucha!=null)
                {
                    if(!escucha.isClosed())
                    {
                        escucha.close();
                    }
                }
                hilo = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try
                        {
                            //Crear el socket en un puerto
                            escucha = new DatagramSocket(Integer.parseInt(textPuerto.getText().toString()));
                            byte[] datos = new byte[1024];
                            DatagramPacket paquete = new DatagramPacket(datos,datos.length);
                            while(true)
                            {
                                escucha.receive(paquete);
                                byte[] information = paquete.getData();
                                recibido = new String(information);
                                recibido=recibido.substring(0,paquete.getLength());
                                if(recibido.compareTo("1")==0)
                                {
                                    if(tablero[0][0]==-3)
                                    {
                                        tablero[0][0]=0;
                                    }
                                }
                                if(recibido.compareTo("2")==0)
                                {
                                    if(tablero[0][1]==-3)
                                    {
                                        tablero[0][1]=0;
                                    }
                                }
                                if(recibido.compareTo("3")==0)
                                {
                                    if(tablero[0][2]==-3)
                                    {
                                        tablero[0][2]=0;
                                    }
                                }
                                if(recibido.compareTo("4")==0)
                                {
                                    if(tablero[1][0]==-3)
                                    {
                                        tablero[1][0]=0;
                                    }
                                }
                                if(recibido.compareTo("5")==0)
                                {
                                    if(tablero[1][1]==-3)
                                    {
                                        tablero[1][1]=0;
                                    }
                                }
                                if(recibido.compareTo("6")==0)
                                {
                                    if(tablero[1][2]==-3)
                                    {
                                        tablero[1][2]=0;
                                    }
                                }
                                if(recibido.compareTo("7")==0)
                                {
                                    if(tablero[2][0]==-3)
                                    {
                                        tablero[2][0]=0;
                                    }
                                }
                                if(recibido.compareTo("8")==0)
                                {
                                    if(tablero[2][1]==-3)
                                    {
                                        tablero[2][1]=0;
                                    }
                                }
                                if(recibido.compareTo("9")==0)
                                {
                                    if(tablero[2][2]==-3)
                                    {
                                        tablero[2][2]=0;
                                    }
                                }
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        pintarTablero();
                                    }
                                });
                                turno=1;
                            }
                        }
                        catch (Exception e)
                        {
                            Log.e("Error hilo", e.toString());
                        }
                    }
                });
                hilo.setDaemon(true);
                hilo.start();
            }
        });

        imageButton1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(turno==0)
                {
                    return;
                }
                //Evaluar que no esté ocupado
                if(tablero[0][0]==-3)
                {
                    //poner 1 en el tablero
                    tablero[0][0]=1;
                    //No está ocupado, mandar número de boton 1
                    try
                    {
                        Thread hiloEnviar=new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try
                                {
                                    DatagramSocket envia = new DatagramSocket();
                                    envia.setBroadcast(true);
                                    byte[] dato="1".getBytes();
                                    DatagramPacket paquete =  new DatagramPacket(dato,dato.length, InetAddress.getByName("255.255.255.255"),Integer.parseInt(textPuerto.getText().toString()));
                                    envia.send(paquete);
                                    envia.send(paquete);
                                    envia.send(paquete);
                                    envia.send(paquete);
                                    envia.send(paquete);

                                    envia.close();
                                    turno=0;
                                }
                                catch (Exception e)
                                {
                                    Log.e("Error Enviar",e.toString());
                                }
                            }
                        });
                        hiloEnviar.setDaemon(true);
                        hiloEnviar.start();
                    }
                    catch (Exception e)
                    {
                        Log.e("Error Hilo",e.toString());
                    }
                    //Actualiza tablero
                    pintarTablero();
                }
            }
        });

        imageButton2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(turno==0)
                {
                    return;
                }
                //Evaluar que no esté ocupado
                if(tablero[0][1]==-3)
                {
                    //poner 1 en el tablero
                    tablero[0][1]=1;
                    //No está ocupado, mandar número de boton 1
                    try
                    {
                        Thread hiloEnviar=new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try
                                {
                                    DatagramSocket envia = new DatagramSocket();
                                    envia.setBroadcast(true);
                                    byte[] dato="2".getBytes();
                                    DatagramPacket paquete =  new DatagramPacket(dato,dato.length, InetAddress.getByName("255.255.255.255"),Integer.parseInt(textPuerto.getText().toString()));
                                    envia.send(paquete);
                                    envia.send(paquete);
                                    envia.send(paquete);
                                    envia.send(paquete);
                                    envia.send(paquete);
                                    envia.close();
                                    turno=0;
                                }
                                catch (Exception e)
                                {
                                    Log.e("Error Enviar",e.toString());
                                }
                            }
                        });
                        hiloEnviar.setDaemon(true);
                        hiloEnviar.start();
                    }
                    catch (Exception e)
                    {
                        Log.e("Error Hilo",e.toString());
                    }
                    //Actualiza tablero
                    pintarTablero();
                }
            }
        });

        imageButton3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(turno==0)
                {
                    return;
                }
                //Evaluar que no esté ocupado
                if(tablero[0][2]==-3)
                {
                    //poner 1 en el tablero
                    tablero[0][2]=1;
                    //No está ocupado, mandar número de boton 1
                    try
                    {
                        Thread hiloEnviar=new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try
                                {
                                    DatagramSocket envia = new DatagramSocket();
                                    envia.setBroadcast(true);
                                    byte[] dato="3".getBytes();
                                    DatagramPacket paquete =  new DatagramPacket(dato,dato.length, InetAddress.getByName("255.255.255.255"),Integer.parseInt(textPuerto.getText().toString()));
                                    envia.send(paquete);
                                    envia.send(paquete);
                                    envia.send(paquete);
                                    envia.send(paquete);
                                    envia.send(paquete);
                                    envia.close();
                                    turno=0;
                                }
                                catch (Exception e)
                                {
                                    Log.e("Error Enviar",e.toString());
                                }
                            }
                        });
                        hiloEnviar.setDaemon(true);
                        hiloEnviar.start();
                    }
                    catch (Exception e)
                    {
                        Log.e("Error Hilo",e.toString());
                    }
                    //Actualiza tablero
                    pintarTablero();
                }
            }
        });

        imageButton4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(turno==0)
                {
                    return;
                }
                //Evaluar que no esté ocupado
                if(tablero[1][0]==-3)
                {
                    //poner 1 en el tablero
                    tablero[1][0]=1;
                    //No está ocupado, mandar número de boton 1
                    try
                    {
                        Thread hiloEnviar=new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try
                                {
                                    DatagramSocket envia = new DatagramSocket();
                                    envia.setBroadcast(true);
                                    byte[] dato="4".getBytes();
                                    DatagramPacket paquete =  new DatagramPacket(dato,dato.length, InetAddress.getByName("255.255.255.255"),Integer.parseInt(textPuerto.getText().toString()));
                                    envia.send(paquete);
                                    envia.send(paquete);
                                    envia.send(paquete);
                                    envia.send(paquete);
                                    envia.send(paquete);
                                    envia.close();
                                    turno=0;
                                }
                                catch (Exception e)
                                {
                                    Log.e("Error Enviar",e.toString());
                                }
                            }
                        });
                        hiloEnviar.setDaemon(true);
                        hiloEnviar.start();
                    }
                    catch (Exception e)
                    {
                        Log.e("Error Hilo",e.toString());
                    }
                    //Actualiza tablero
                    pintarTablero();
                }
            }
        });

        imageButton5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(turno==0)
                {
                    return;
                }
                //Evaluar que no esté ocupado
                if(tablero[1][1]==-3)
                {
                    //poner 1 en el tablero
                    tablero[1][1]=1;
                    //No está ocupado, mandar número de boton 1
                    try
                    {
                        Thread hiloEnviar=new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try
                                {
                                    DatagramSocket envia = new DatagramSocket();
                                    envia.setBroadcast(true);
                                    byte[] dato="5".getBytes();
                                    DatagramPacket paquete =  new DatagramPacket(dato,dato.length, InetAddress.getByName("255.255.255.255"),Integer.parseInt(textPuerto.getText().toString()));
                                    envia.send(paquete);
                                    envia.send(paquete);
                                    envia.send(paquete);
                                    envia.send(paquete);
                                    envia.send(paquete);
                                    envia.close();
                                    turno=0;
                                }
                                catch (Exception e)
                                {
                                    Log.e("Error Enviar",e.toString());
                                }
                            }
                        });
                        hiloEnviar.setDaemon(true);
                        hiloEnviar.start();
                    }
                    catch (Exception e)
                    {
                        Log.e("Error Hilo",e.toString());
                    }
                    //Actualiza tablero
                    pintarTablero();
                }
            }
        });

        imageButton6.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(turno==0)
                {
                    return;
                }
                //Evaluar que no esté ocupado
                if(tablero[1][2]==-3)
                {
                    //poner 1 en el tablero
                    tablero[1][2]=1;
                    //No está ocupado, mandar número de boton 1
                    try
                    {
                        Thread hiloEnviar=new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try
                                {
                                    DatagramSocket envia = new DatagramSocket();
                                    envia.setBroadcast(true);
                                    byte[] dato="6".getBytes();
                                    DatagramPacket paquete =  new DatagramPacket(dato,dato.length, InetAddress.getByName("255.255.255.255"),Integer.parseInt(textPuerto.getText().toString()));
                                    envia.send(paquete);
                                    envia.send(paquete);
                                    envia.send(paquete);
                                    envia.send(paquete);
                                    envia.send(paquete);
                                    envia.close();
                                    turno=0;
                                }
                                catch (Exception e)
                                {
                                    Log.e("Error Enviar",e.toString());
                                }
                            }
                        });
                        hiloEnviar.setDaemon(true);
                        hiloEnviar.start();
                    }
                    catch (Exception e)
                    {
                        Log.e("Error Hilo",e.toString());
                    }
                    //Actualiza tablero
                    pintarTablero();
                }
            }
        });

        imageButton7.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(turno==0)
                {
                    return;
                }
                //Evaluar que no esté ocupado
                if(tablero[2][0]==-3)
                {
                    //poner 1 en el tablero
                    tablero[2][0]=1;
                    //No está ocupado, mandar número de boton 1
                    try
                    {
                        Thread hiloEnviar=new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try
                                {
                                    DatagramSocket envia = new DatagramSocket();
                                    envia.setBroadcast(true);
                                    byte[] dato="7".getBytes();
                                    DatagramPacket paquete =  new DatagramPacket(dato,dato.length, InetAddress.getByName("255.255.255.255"),Integer.parseInt(textPuerto.getText().toString()));
                                    envia.send(paquete);
                                    envia.send(paquete);
                                    envia.send(paquete);
                                    envia.send(paquete);
                                    envia.send(paquete);
                                    envia.close();
                                    turno=0;
                                }
                                catch (Exception e)
                                {
                                    Log.e("Error Enviar",e.toString());
                                }
                            }
                        });
                        hiloEnviar.setDaemon(true);
                        hiloEnviar.start();
                    }
                    catch (Exception e)
                    {
                        Log.e("Error Hilo",e.toString());
                    }
                    //Actualiza tablero
                    pintarTablero();
                }
            }
        });

        imageButton8.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(turno==0)
                {
                    return;
                }
                //Evaluar que no esté ocupado
                if(tablero[2][1]==-3)
                {
                    //poner 1 en el tablero
                    tablero[2][1]=1;
                    //No está ocupado, mandar número de boton 1
                    try
                    {
                        Thread hiloEnviar=new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try
                                {
                                    DatagramSocket envia = new DatagramSocket();
                                    envia.setBroadcast(true);
                                    byte[] dato="8".getBytes();
                                    DatagramPacket paquete =  new DatagramPacket(dato,dato.length, InetAddress.getByName("255.255.255.255"),Integer.parseInt(textPuerto.getText().toString()));
                                    envia.send(paquete);
                                    envia.send(paquete);
                                    envia.send(paquete);
                                    envia.send(paquete);
                                    envia.send(paquete);
                                    envia.close();
                                    turno=0;
                                }
                                catch (Exception e)
                                {
                                    Log.e("Error Enviar",e.toString());
                                }
                            }
                        });
                        hiloEnviar.setDaemon(true);
                        hiloEnviar.start();
                    }
                    catch (Exception e)
                    {
                        Log.e("Error Hilo",e.toString());
                    }
                    //Actualiza tablero
                    pintarTablero();
                }
            }
        });

        imageButton9.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(turno==0)
                {
                    return;
                }
                //Evaluar que no esté ocupado
                if(tablero[2][2]==-3)
                {
                    //poner 1 en el tablero
                    tablero[2][2]=1;
                    //No está ocupado, mandar número de boton 1
                    try
                    {
                        Thread hiloEnviar=new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try
                                {
                                    DatagramSocket envia = new DatagramSocket();
                                    envia.setBroadcast(true);
                                    byte[] dato="9".getBytes();
                                    DatagramPacket paquete =  new DatagramPacket(dato,dato.length, InetAddress.getByName("255.255.255.255"),Integer.parseInt(textPuerto.getText().toString()));
                                    envia.send(paquete);
                                    envia.send(paquete);
                                    envia.send(paquete);
                                    envia.send(paquete);
                                    envia.send(paquete);
                                    envia.close();
                                    turno=0;
                                }
                                catch (Exception e)
                                {
                                    Log.e("Error Enviar",e.toString());
                                }
                            }
                        });
                        hiloEnviar.setDaemon(true);
                        hiloEnviar.start();
                    }
                    catch (Exception e)
                    {
                        Log.e("Error Hilo",e.toString());
                    }
                    //Actualiza tablero
                    pintarTablero();
                }
            }
        });
    }

    public void pintarTablero()
    {
        //Recorrer el arreglo tablero
        //Si tiene un 0 pintar Circulo
        //Si tiene un 1 pintar Cruz
        //Si tiene un -3 pintar el fondo
        if(tablero[0][0]==-3)
        {
            imageButton1.setImageDrawable(getResources().getDrawable(R.mipmap.ffondo));
        }
        if(tablero[0][0]==0)
        {
            imageButton1.setImageDrawable(getResources().getDrawable(R.mipmap.o));
        }
        if(tablero[0][0]==1)
        {
            imageButton1.setImageDrawable(getResources().getDrawable(R.mipmap.x));
        }

        if(tablero[0][1]==-3)
        {
            imageButton2.setImageDrawable(getResources().getDrawable(R.mipmap.ffondo));
        }
        if(tablero[0][1]==0)
        {
            imageButton2.setImageDrawable(getResources().getDrawable(R.mipmap.o));
        }
        if(tablero[0][1]==1)
        {
            imageButton2.setImageDrawable(getResources().getDrawable(R.mipmap.x));
        }

        if(tablero[0][2]==-3)
        {
            imageButton3.setImageDrawable(getResources().getDrawable(R.mipmap.ffondo));
        }
        if(tablero[0][2]==0)
        {
            imageButton3.setImageDrawable(getResources().getDrawable(R.mipmap.o));
        }
        if(tablero[0][2]==1)
        {
            imageButton3.setImageDrawable(getResources().getDrawable(R.mipmap.x));
        }

        if(tablero[1][0]==-3)
        {
            imageButton4.setImageDrawable(getResources().getDrawable(R.mipmap.ffondo));
        }
        if(tablero[1][0]==0)
        {
            imageButton4.setImageDrawable(getResources().getDrawable(R.mipmap.o));
        }
        if(tablero[1][0]==1)
        {
            imageButton4.setImageDrawable(getResources().getDrawable(R.mipmap.x));
        }

        if(tablero[1][1]==-3)
        {
            imageButton5.setImageDrawable(getResources().getDrawable(R.mipmap.ffondo));
        }
        if(tablero[1][1]==0)
        {
            imageButton5.setImageDrawable(getResources().getDrawable(R.mipmap.o));
        }
        if(tablero[1][1]==1)
        {
            imageButton5.setImageDrawable(getResources().getDrawable(R.mipmap.x));
        }

        if(tablero[1][2]==-3)
        {
            imageButton6.setImageDrawable(getResources().getDrawable(R.mipmap.ffondo));
        }
        if(tablero[1][2]==0)
        {
            imageButton6.setImageDrawable(getResources().getDrawable(R.mipmap.o));
        }
        if(tablero[1][2]==1)
        {
            imageButton6.setImageDrawable(getResources().getDrawable(R.mipmap.x));
        }

        if(tablero[2][0]==-3)
        {
            imageButton7.setImageDrawable(getResources().getDrawable(R.mipmap.ffondo));
        }
        if(tablero[2][0]==0)
        {
            imageButton7.setImageDrawable(getResources().getDrawable(R.mipmap.o));
        }
        if(tablero[2][0]==1)
        {
            imageButton7.setImageDrawable(getResources().getDrawable(R.mipmap.x));
        }

        if(tablero[2][1]==-3)
        {
            imageButton8.setImageDrawable(getResources().getDrawable(R.mipmap.ffondo));
        }
        if(tablero[2][1]==0)
        {
            imageButton8.setImageDrawable(getResources().getDrawable(R.mipmap.o));
        }
        if(tablero[2][1]==1)
        {
            imageButton8.setImageDrawable(getResources().getDrawable(R.mipmap.x));
        }

        if(tablero[2][2]==-3)
        {
            imageButton9.setImageDrawable(getResources().getDrawable(R.mipmap.ffondo));
        }
        if(tablero[2][2]==0)
        {
            imageButton9.setImageDrawable(getResources().getDrawable(R.mipmap.o));
        }
        if(tablero[2][2]==1)
        {
            imageButton9.setImageDrawable(getResources().getDrawable(R.mipmap.x));
        }

        if((tablero[0][0]+tablero[0][1]+tablero[0][2]==3)||
                (tablero[1][0]+tablero[1][1]+tablero[1][2]==3)||
                (tablero[2][0]+tablero[2][1]+tablero[2][2]==3))
        {
            Toast.makeText(getApplicationContext(),"Ganaste",Toast.LENGTH_LONG).show();
        }

        if((tablero[0][0]+tablero[0][1]+tablero[0][2]==0)||
                (tablero[1][0]+tablero[1][1]+tablero[1][2]==0)||
                (tablero[2][0]+tablero[2][1]+tablero[2][2]==0))
        {
            Toast.makeText(getApplicationContext(),"Perdiste",Toast.LENGTH_LONG).show();
        }

        if((tablero[0][0]+tablero[1][0]+tablero[2][0]==3)||
                (tablero[0][1]+tablero[1][1]+tablero[2][1]==3)||
                (tablero[0][2]+tablero[1][2]+tablero[2][2]==3))
        {
            Toast.makeText(getApplicationContext(),"Ganaste",Toast.LENGTH_LONG).show();
        }

        if((tablero[0][0]+tablero[1][0]+tablero[2][0]==0)||
                (tablero[0][1]+tablero[1][1]+tablero[2][1]==0)||
                (tablero[0][2]+tablero[1][2]+tablero[2][2]==0))
        {
            Toast.makeText(getApplicationContext(),"Perdiste",Toast.LENGTH_LONG).show();
        }
        if((tablero[0][0]+tablero[1][0]+tablero[2][0]==3)||
                (tablero[0][0]+tablero[1][1]+tablero[2][2]==3)||
                (tablero[0][2]+tablero[1][1]+tablero[2][0]==3))
        {
            Toast.makeText(getApplicationContext(),"Ganaste",Toast.LENGTH_LONG).show();
        }

        if((tablero[0][0]+tablero[1][0]+tablero[2][0]==0)||
                (tablero[0][0]+tablero[1][1]+tablero[2][2]==0)||
                (tablero[0][2]+tablero[1][1]+tablero[2][0]==0))
        {
            Toast.makeText(getApplicationContext(),"Perdiste",Toast.LENGTH_LONG).show();
        }
    }
}
