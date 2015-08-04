package ru.lukutin.main;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Sergey on 8/2/2015.
 */
public class StringPool {


    /**
     * Testing String.intern.
     *
     * Run this class at least with -verbose:gc JVM parameter.
     */

        public static void main( String[] args ) {
            multiThreadedInternTest(10000, 1000);
            //testStringPoolGarbageCollection();
            //testLongLoop();
        }

        /**
         * Use this method to see where interned strings are stored
         * and how many of them can you fit for the given heap size.
         */
        private static void testLongLoop()
        {
            test( 1000 * 1000 * 1000 );
            //uncomment the following line to see the hand-written cache performance
            //testManual( 1000 * 1000 * 1000 );
        }

        /**
         * Use this method to check that not used interned strings are garbage collected.
         */
        private static void testStringPoolGarbageCollection()
        {
            //first method call - use it as a reference
            test( 1000 * 1000 );
            //we are going to clean the cache here.
            System.gc();
            //check the memory consumption and how long does it take to intern strings
            //in the second method call.
            test( 1000 * 1000 );
        }

        private static void test( final int cnt )
        {
            final List<String> lst = new ArrayList<String>( 100 );
            long start = System.currentTimeMillis();
            for ( int i = 0; i < cnt; ++i )
            {
                final String str = "Very long test string, which tells you about something " +
                        "very-very important, definitely deserving to be interned #" + i;
                //uncomment the following line to test dependency from string length
                //            final String str = Integer.toString( i );
                lst.add( str.intern() );
                if ( i % 10000 == 0 )
                {
                    System.out.println( i + "; time = " + ( System.currentTimeMillis() - start ) / 1000.0 + " sec" );
                    start = System.currentTimeMillis();
                }
            }
            System.out.println( "Total length = " + lst.size() );
        }

        private static final WeakHashMap<String, WeakReference<String>> s_manualCache =
                new WeakHashMap<String, WeakReference<String>>( 100000 );

        private static String manualIntern( final String str )
        {
            final WeakReference<String> cached = s_manualCache.get( str );
            if ( cached != null )
            {
                final String value = cached.get();
                if ( value != null )
                    return value;
            }
            s_manualCache.put( str, new WeakReference<String>( str ) );
            return str;
        }

        private static void testManual( final int cnt )
        {
            final List<String> lst = new ArrayList<String>( 100 );
            long start = System.currentTimeMillis();
            for ( int i = 0; i < cnt; ++i )
            {
                final String str = "Very long test string, which tells you about something " +
                        "very-very important, definitely deserving to be interned #" + i;
                lst.add( manualIntern( str ) );
                if ( i % 10000 == 0 )
                {
                    System.out.println( i + "; manual time = " + ( System.currentTimeMillis() - start ) / 1000.0 + " sec" );
                    start = System.currentTimeMillis();
                }
            }
            System.out.println( "Total length = " + lst.size() );
        }


    private static void multiThreadedInternTest( final int threads, final int cnt )
    {
        final CountDownLatch latch = new CountDownLatch( threads );
        for ( int i = 0; i < threads; ++i )
        {
            final int threadNo = i;
            final Runnable task = new Runnable() {
                @Override
                public void run() {
                    latch.countDown();
                    try {
                        latch.await(); //start all threads simultaneously
                    } catch ( InterruptedException ignored ) {
                    }

                    final List<String> lst = new ArrayList<String>( 100 );
                    long start = System.currentTimeMillis();
                    for ( int i = 0; i < cnt; ++i )
                    {
                        //this line is used in 8 writers scenario
                        final String str = "Thread #" + threadNo + " : " + i;
                        //use the following line for 1 writer, 7 readers scenario
                        //final String str = "Thread #0 : " + i;
                        lst.add( str.intern() );
                        if ( i % 10000 == 0 )
                        {
                            System.out.println( "Thread # " + threadNo + " : " + i +
                                    "; time = " + ( System.currentTimeMillis() - start ) / 1000.0 + " sec" );
                            start = System.currentTimeMillis();
                        }
                    }
                    System.out.println( "Total length = " + lst.size() );
                }
            };
            new Thread( task ).start();
        }
    }
}
