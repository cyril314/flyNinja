package conf;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import ninja.utils.NinjaProperties;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.guice.MyBatisModule;
import org.mybatis.guice.datasource.builtin.PooledDataSourceProvider;
import services.RedisService;
import services.UserService;

/**
 * @className: NinjaModule
 * @description:
 * @author: Aim
 * @date: 2023/4/3
 **/
@Singleton
public class Module extends AbstractModule {

    private final NinjaProperties ninjaProperties;

    public Module(NinjaProperties ninjaProperties) {
        this.ninjaProperties = ninjaProperties;
    }

    public void configure() {
        this.install(createMyBatisModule());
        this.bind(RedisService.class);
        this.bind(UserService.class);
    }

    private com.google.inject.Module createMyBatisModule() {
        return new MyBatisModule() {
            @Override
            protected void initialize() {
                environmentId("production");
                bindDataSourceProviderType(PooledDataSourceProvider.class);
                bindTransactionFactoryType(JdbcTransactionFactory.class);
                //bind(SqlSessionFactory.class).toProvider(SqlSessionFactoryProvider.class).in(Scopes.SINGLETON);

                addSimpleAliases("models");
                addMapperClasses("mapper");

                Names.bindProperties(binder(), ninjaProperties.getAllCurrentNinjaProperties());
            }
        };
    }
}
