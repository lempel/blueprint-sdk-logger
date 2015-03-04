/*
 License:

 blueprint-sdk is licensed under the terms of Eclipse Public License(EPL) v1.0
 (http://www.eclipse.org/legal/epl-v10.html)


 Distribution:

 International - http://code.google.com/p/blueprint-sdk
 South Korea - http://lempel.egloos.com


 Background:

 blueprint-sdk is a java software development kit to protect other open source
 software licenses. It's intended to provide light weight APIs for blueprints.
 Well... at least trying to.

 There are so many great open source projects now. Back in year 2000, there
 were not much to use. Even JDBC drivers were rare back then. Naturally, I have
 to implement many things by myself. Especially dynamic class loading, networking,
 scripting, logging and database interactions. It was time consuming. Now I can
 take my picks from open source projects.

 But I still need my own APIs. Most of my clients just don't understand open
 source licenses. They always want to have their own versions of open source
 projects but don't want to publish derivative works. They shouldn't use open
 source projects in the first place. So I need to have my own open source project
 to be free from derivation terms and also as a mediator between other open
 source projects and my client's requirements.

 Primary purpose of blueprint-sdk is not to violate other open source project's
 license terms.


 To committers:

 License terms of the other software used by your source code should not be
 violated by using your source code. That's why blueprint-sdk is made for.
 Without that, all your contributions are welcomed and appreciated.
 */
package blueprint.sdk.logger;

import blueprint.sdk.util.StringUtil;

import java.io.*;
import java.util.Calendar;

/**
 * Extended PrintStream for logger.<br>
 * Prints timestamp always.<br>
 *
 * @author Sangmin Lee
 * @since 2002. 07. 12
 */
public class LogStream extends PrintStream {
    private transient Calendar cal;

    /**
     * Time Stamp
     */
    private transient byte[] timeStamp = new byte[21];

    @SuppressWarnings("UnusedDeclaration")
    public LogStream(OutputStream stream) {
        super(stream);
    }

    @SuppressWarnings("SameParameterValue")
    public LogStream(OutputStream stream, boolean autoFlush) {
        super(stream, autoFlush);
    }

    public LogStream(String fileName) throws FileNotFoundException {
        super(new FileOutputStream(fileName, true));
    }

    public void println(boolean val) {
        synchronized (this) {
            cal = Calendar.getInstance();
            printTimeStamp();
            super.println(val);
        }
    }

    public void println(char val) {
        synchronized (this) {
            cal = Calendar.getInstance();
            printTimeStamp();
            super.println(val);
        }
    }

    public void println(int val) {
        synchronized (this) {
            cal = Calendar.getInstance();
            printTimeStamp();
            super.println(val);
        }
    }

    public void println(long val) {
        synchronized (this) {
            cal = Calendar.getInstance();
            printTimeStamp();
            super.println(val);
        }
    }

    public void println(float val) {
        synchronized (this) {
            cal = Calendar.getInstance();
            printTimeStamp();
            super.println(val);
        }
    }

    public void println(double val) {
        synchronized (this) {
            cal = Calendar.getInstance();
            printTimeStamp();
            super.println(val);
        }
    }

    public void println(@SuppressWarnings("NullableProblems") char[] val) {
        synchronized (this) {
            cal = Calendar.getInstance();
            printTimeStamp();
            super.println(val == null ? "null" : val);
        }
    }

    public void println(String val) {
        synchronized (this) {
            cal = Calendar.getInstance();
            printTimeStamp();
            super.println(val);
        }
    }

    public void println(Object val) {
        synchronized (this) {
            cal = Calendar.getInstance();
            printTimeStamp();
            super.println(val);
        }
    }

    void printTimeStamp() {
        timeStamp[0] = '[';
        timeStamp[3] = '/';
        timeStamp[6] = ' ';
        timeStamp[9] = ':';
        timeStamp[12] = ':';
        timeStamp[15] = '.';
        timeStamp[19] = ']';
        timeStamp[20] = ' ';

        byte[] tempArray;
        tempArray = StringUtil.lpadZero(Integer.toString(cal.get(Calendar.MONTH) + 1), 2).getBytes();
        System.arraycopy(tempArray, 0, timeStamp, 1, 2);
        tempArray = StringUtil.lpadZero(Integer.toString(cal.get(Calendar.DAY_OF_MONTH)), 2).getBytes();
        System.arraycopy(tempArray, 0, timeStamp, 4, 2);
        tempArray = StringUtil.lpadZero(Integer.toString(cal.get(Calendar.HOUR_OF_DAY)), 2).getBytes();
        System.arraycopy(tempArray, 0, timeStamp, 7, 2);
        tempArray = StringUtil.lpadZero(Integer.toString(cal.get(Calendar.MINUTE)), 2).getBytes();
        System.arraycopy(tempArray, 0, timeStamp, 10, 2);
        tempArray = StringUtil.lpadZero(Integer.toString(cal.get(Calendar.SECOND)), 2).getBytes();
        System.arraycopy(tempArray, 0, timeStamp, 13, 2);
        tempArray = StringUtil.lpadZero(Integer.toString(cal.get(Calendar.MILLISECOND)), 3).getBytes();
        System.arraycopy(tempArray, 0, timeStamp, 16, 3);

        super.print(new String(timeStamp));
    }

    @Override
    protected void finalize() throws Throwable {
        cal = null;
        timeStamp = null;

        super.finalize();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.io.PrintStream#close()
     */
    @Override
    public void close() {
        // do not close original System.out/err
        SystemAppender sysAppender = new SystemAppender();
        if (sysAppender.getOutStream().equals(out) || sysAppender.getErrStream().equals(out)) {
            out = new ByteArrayOutputStream(1);
        }

        super.close();
    }
}