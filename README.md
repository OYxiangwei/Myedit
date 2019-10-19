# Myedit
java 实现简单的编辑器
1、在 FileWindow 类中编写实现 GUI 的代码继承JFrame实现了ActionListener 和Runnable接口，需要两个JPanel（Center、North）把各个组件Add上去。
1.1、actionPerformed 方法的实现，根据传入的ActionEvent.getSource()来判断显示的区域，当是compiler和run时判断这两个的线程是否存活，是否新建线程，启动线程，
1.2、run 方法实现compiler和run线程的具体内容，使用到Runtime.getRuntime()、getText()、FileOutputStream、BufferedInputStream，Thread.currentThread()
2、main
