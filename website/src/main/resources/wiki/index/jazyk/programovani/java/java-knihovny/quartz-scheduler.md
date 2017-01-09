## Quartz Scheduler

Pro plánování a automatické spouštění úloh v aplikacích založených na technologii Java lze využít službu [Quartz Scheduler](http://quartz-scheduler.org). Je velmi flexibilní a hodí se jak pro malé aplikace založené na Java SE, tak i pro obrovské projekty postavené na Java EE. Údaje o spuštěných a naplánovaných úlohách mohou být uloženy v paměti i v databázi a využít lze i transakce JTA.

Základními třídami, se kterými Quartz Scheduler pracuje, jsou tyto:

- **Scheduler** = plánovací služba, která shromažďuje údaje o úlohách a automaticky je spouští; instance se vytváří například tovární metodou třídy **StdSchedulerFactory**
- **JobDetail** = údaje o úloze, ve kterých se nachází hlavně vlastní třída úlohy; instance se vytváří pomocí třídy **JobBuilder**
- **Trigger** = spouštěcí mechanismus nějaké úlohy, který specifikuje, kdy se má úloha spouštět (například v pracovní den každou hodinu); instance se vytváří pomocí třídy **TriggerBuilder** (návrhový vzor Builder)

### Příklad použití

#### Úloha

```java
public class HelloJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Hello world.");
    }
}
```

### Spuštění úlohy

```java
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class Executor {
    public static void main(String[] args) throws SchedulerException {
        // vytvořit novou instanci plánovací služby
        final Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        
        // spustit službu
        scheduler.start();

        // vytvořit údaje o úloze
        final JobDetail job = JobBuilder
                .newJob()
                .ofType(HelloJob.class)
                .build();
        
        // vytvořit spouštěcí mechanismus (= každou sekundu, navždy)
        final Trigger trigger = TriggerBuilder
                .newTrigger()
                .forJob(job)
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever())
                .startNow()
                .build();

        // přidat úlohu do služby a naplánovat její spuštění
        scheduler.addJob(job, true);
        scheduler.scheduleJob(trigger);

        try {
            // počkat 10 sekund, aby se úloha stihla několikrát spustit
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // NOP
        }

        // ukončit službu (toto je nutné nakonec provést)
        scheduler.shutdown();
    }
}
```

### Reference

- http://quartz-scheduler.org/documentation