package step.learning.ioc;

import com.google.inject.AbstractModule;

public class ConfigModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(HashService.class).to(Md5HashService.class);
    }

}
