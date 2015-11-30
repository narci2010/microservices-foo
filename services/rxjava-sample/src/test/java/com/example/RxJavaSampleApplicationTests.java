package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RxJavaSampleApplication.class)
public class RxJavaSampleApplicationTests {

    private final String PARAM = "Hello World.";
    private final String RESULT = "Hello World.";

    @Test
    public void boilerplate(){
        Observable<String> observable = Observable.create(
            new Observable.OnSubscribe<String>(){

                @Override
                public void call(Subscriber<? super String> sub) {
                    sub.onNext(PARAM);
                    sub.onCompleted();
                }
            }
        );
        Subscriber<String> subscriber = new Subscriber<String>(){

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(String s) {
                // Do something.
            }
        };

        observable.subscribe(subscriber);
    }

    @Test
    public void boilerplate_shorter(){
        Observable<String> observable = Observable.just(PARAM);
        Action1<String> onNextAction = new Action1<String>() {
            @Override
            public void call(String s) {
                assertEquals(RESULT,s);
            }
        };
        observable.subscribe(onNextAction);
    }

	@Test
	public void lambada() {
        Observable<String> observable = Observable.just(PARAM);
        observable.subscribe(
            s -> {
                assertEquals(RESULT,s);
            }
        );
	}

    @Test
    public void map() {
        Observable.just(PARAM).map( s -> s + " - map result").subscribe(
            s -> assertEquals(RESULT + " - map result" ,s)
        );
    }

    @Test
    public void map_to_different_type(){
        Observable.just(PARAM)
            .map(s -> s.hashCode())
            .subscribe(s -> assertEquals(s.hashCode(),PARAM.hashCode()));
    }

    @Test
    public void from(){
        List<String> list = Arrays.asList(PARAM,PARAM,PARAM);
        Observable.from(list)
            .subscribe(s -> {
                assertEquals(PARAM,s);
                System.out.println(s);
            });
    }

    @Test
    public void filter(){
        List<String> list = Arrays.asList("Blah Blah", PARAM, "Blah Blah again");
        Observable.from(list)
            .filter(s -> s.equals(PARAM))
            .subscribe( s -> assertEquals(PARAM,s));
    }

    @Test
    public void take(){
        List<String> list = Arrays.asList("Blah Blah", PARAM, "Blah Blah again");
        Observable.from(list)
            .take(2)
            .subscribe(s -> System.out.println(s));
    }

}
