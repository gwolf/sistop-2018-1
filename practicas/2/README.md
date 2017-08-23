# Depuración por trazas

    Práctica creada el 22.08.2017
	Entrega en tiempo: Jueves 31.08.2017

Para afianzar el tema de _excepciones, interrupciones y llamadas al
sistema_, les propongo el siguiente ejercicio: Encontrar qué pasos
sigue la ejecución de un programa sin contar con su código fuente.

Lo que van a hacer es, de cierto modo, equiparable a una _ingeniería
inversa de caja negra_: Usar un programa que intercepte las llamadas
al sistema que efectúe su programa, y de esa manera averiguar qué
servicios le está pidiendo al sistema operativo. Pueden revisar una
muy (tal vez demasiado) breve explicación en las
[láminas 32 y 33 de la presentación](http://gwolf.sistop.org/laminas/03-relacion-con-el-hardware.pdf#page=32)
o en la
[sección 2.7.1 del libro](http://sistop.org/pdf/sistemas_operativos.pdf)).

Esta práctica consiste en realizar algo similar a lo que demostré en
clase. Esto es:

- Elijan un programa _sencillo_, y _tracen_ su ejecución. Ojo: Trazar
  la ejecución de un programa con interfaz gráfica es muy complejo;
  desenterrar la lógica del programa de entre todos los elementos
  gráficos y de eventos es casi imposible. Busquen algo que puedan
  ejecutar desde su línea de comando.
    - En Linux, con `strace`. En MacOS, pueden usar `ktrace` o
      `dtrace`.
    - En Windows hay muchos programas, aunque hasta donde puedo
      entender, ninguno viene preinstalado por default. Me encontré
      con el [Dr. Memory Framework](http://drmemory.org/), que dice
      tener un _Strace for Windows_.
    - Obviamente, si encuentran otra herramienta, ¡bienvenida!
- La salida de la ejecución probablemente va a ser _muy_
  grande. Hagan una revisión rápida a ojo para buscar una región
  interesante, y elijan unas _15 llamadas consecutivas_ en que se
  quieran enfocar
    - En clase les mostré que hay una sección que constituye el inicio
      de puesta en marcha del programa; abordaremos parte de esta
      lógica en el transcurso del curso. En el ejemplo que presento en
      el libro (el de las láminas está muy recortado), esta comprende
      aproximadamente hasta la línea 25. Ignoren este primer pedazo.
    - _Ojo:_ No hace falta que detallen las 15 llamadas una tras
      otra. Si tenemos grupos de llamadas relacionadas, ahórrense el
      describir a cada una de ellas. Hay llamadas obvias y aburridas,
      como `close` o `munmap` que no requieren profundizar.
- Expliquen qué comprenden o qué intuyen de esa porción de la
  ejecución.
    - ¿Línea por línea? ¿Explicando grupos de llamadas? De ustedes
      depende

## Instrucciones de entrega

- Esta práctica es para entrega individual.
- La entrega se hará por Git, siguiendo el esquema de directorios
  especificado en el
  [punto 4 de la práctica 1](https://github.com/gwolf/sistop-2017-2/blob/master/practicas/1/README.md),
  y se considera entregado en el momento en que generen el _pull
  request_ correspondiente.
- Pueden entregar un documento de texto, un archivo PDF, las
  fotografías de pantallazos impresos y anotados en pluma, _como les
  acomode realizar esta práctica_.
- Deben indicar:
    - En qué sistema operativo trabajaron
    - Qué programa emplearon para obtener la traza
    - Qué programa objetivo trazaron
        - ¿Por qué eligieron este programa?
    - Sus observaciones / resultados

## Recursos adicionales

- En sistemas Unix, recuerden la máxima: _man es tu amigo_. Si quieren
  saber qué hace una llamada al sistema, revisen la sección 2 del
  manual. Esto es, del ejemplo anterior: Si quieren saber qué hace
  `fstat`, basta con que escriban desde la terminal `man 2 fstat`, y
  el título de la página les indica `get file status` — Obtiene la
  información acerca de determinado archivo.
    - Claro está, pueden seguir leyendo el texto de la página para
      comprender qué significan los argumentos que recibe y los
      códigos de retorno que entrega.
- Si les interesa revisar una página del manual y no tienen un sistema
  Unix a la mano, pueden entrar a
  [Debian Manpages](https://manpages.debian.org/) y obtener la misma
  información para un sistema Debian
    - Con las ligas en la parte derecha de la pantalla pueden
      consultar las secciones y páginas relacionadas
- Las herramientas de traza son muchas veces importantes bloques de
  construcción para diferentes tareas de monitoreo. Pueden referirse
  por ejemplo a
  [Top 10 DTrace scripts for MacOS X](http://dtrace.org/blogs/brendan/2011/10/10/top-10-dtrace-scripts-for-mac-os-x/)
  como un ejemplo de cómo se obtiene esta información en MacOS
- La página de manual de `strace` les presenta cómo afinar sus
  consultas, obtener datos totalizados, ver cuánto tiempo toma cada
  llamada al sistema, etc. Pueden jugar con esta salida
  también. ¿Encontraron algo interesante especificando estas opciones?

