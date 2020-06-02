package com.fertilizertool.database;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.fertilizertool.model.Fertilizers;
import com.fertilizertool.model.VillageMaster;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

  /*  @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/dev");
        dataSource.setUsername("root");
        dataSource.setPassword("root");

        return dataSource;
    }*/

    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor("spring_batch");
        asyncTaskExecutor.setConcurrencyLimit(5);
        return asyncTaskExecutor;
    }

    @Bean
    public FlatFileItemReader<VillageMaster> villageReader() {
        FlatFileItemReader<VillageMaster> reader = new FlatFileItemReader<VillageMaster>();
        reader.setResource(new ClassPathResource("MasterVillageList.csv"));
        reader.setLinesToSkip(1);
        reader.setLineMapper(new DefaultLineMapper<VillageMaster>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[]{"StateCode", "StateName", "DistrictName", "DistrictCode", "SubDistrict", "SubDisCode", "Village", "VillageCode",
                        "Ns", "Ps", "Ks", "pH", "OC", "Bs", "Cus", "Fes", "Mns", "Ss", "Zns"});
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<VillageMaster>() {{
                setTargetType(VillageMaster.class);
            }});

        }});

        return reader;
    }

    @Bean
    public FlatFileItemReader<Fertilizers> fertilizerReader() {
        FlatFileItemReader<Fertilizers> reader = new FlatFileItemReader<Fertilizers>();
        reader.setResource(new ClassPathResource("FertilizerList.csv"));
        reader.setLinesToSkip(1);
        reader.setLineMapper(new DefaultLineMapper<Fertilizers>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[]{"Id", "fertilizerSources", "N", "P", "K", "priceBag", "priceCart", "priceTractor",
                        "type"});
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Fertilizers>() {{
                setTargetType(Fertilizers.class);
            }});

        }});

        return reader;
    }

    @Bean
    public VillageProcessor processor() {
        return new VillageProcessor();
    }

    @Bean
    public FerlilizerProcessor fertilizerProcessor() {
        return new FerlilizerProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<VillageMaster> writer() {
        JdbcBatchItemWriter<VillageMaster> writer = new JdbcBatchItemWriter<VillageMaster>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<VillageMaster>());
        writer.setSql("INSERT INTO villagemaster(StateCode,StateName,DistrictName,DistrictCode,subdistrictname,subdistrictcode,villagename,villagecode,Ns,Ps,Ks,pH,OC,Bs,Cus,Fes,Mns,Ss,Zns) VALUES (:StateCode,:StateName,:DistrictName,:DistrictCode,:SubDistrict,:SubDisCode,:Village,:VillageCode,:Ns,:Ps,:Ks,:pH,:OC,:Bs,:Cus,:Fes,:Mns,:Ss,:Zns)");
        writer.setDataSource(dataSource);

        return writer;
    }


    @Bean
    public JdbcBatchItemWriter<Fertilizers> Ferlilizerwriter() {
        JdbcBatchItemWriter<Fertilizers> writer = new JdbcBatchItemWriter<Fertilizers>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Fertilizers>());
        writer.setSql("INSERT INTO fertilizerslist(id,fertilizersources,n,p,k,pricebag,pricecart,pricetractor,type) VALUES (:Id,:fertilizerSources,:N,:P,:K,:priceBag,:priceCart,:priceTractor,:type)");
        writer.setDataSource(dataSource);

        return writer;
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1").<VillageMaster, VillageMaster>chunk(10000)
                .reader(villageReader())
                .processor(processor())
                .writer(writer()).taskExecutor(taskExecutor())
                .build();
    }


    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2").<Fertilizers, Fertilizers>chunk(10000)
                .reader(fertilizerReader())
                .processor(fertilizerProcessor())
                .writer(Ferlilizerwriter()).taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Job villageImportJob(JobCompletionListener listener, Step step1, Step step2) {
        return jobBuilderFactory.get("villageImportJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(step1())
                .next(step2())
                .build();
    }

}
