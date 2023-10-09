package step.learning.ioc;

import step.learning.ioc.services.hash.HashService;
import step.learning.ioc.services.random.RandomService;

import javax.inject.Inject;
import javax.inject.Named;

public class IocDemo
{
//    @Inject
//    private HashService hashService;
//    @Inject
//    private Md5HashService md5HashService;

//    private final HashService hashService;
//    @Inject
//    public IocDemo(HashService hashService)
//    {
//        this.hashService = hashService;
//    }
//    @Inject private HashService hashService2;
//    @Inject @Named("Hash128") private HashService hashService128;

    private final HashService digestHashService;
    private final HashService dsaHashService;
    private final RandomService randomService;
    @Inject
    public IocDemo(
            @Named("Digest-Hash") HashService digestHashService,
            @Named("DSA-Hash") HashService dsaHashService,
            RandomService randomService)
    {
        this.digestHashService = digestHashService;
        this.dsaHashService = dsaHashService;
        this.randomService = randomService;
    }

    public void run()
    {
        System.out.println("IoC Demo");
        System.out.println("Digest-Hash [MD5] : " + digestHashService.hash("IoC Demo") );
        System.out.println("DSA-Hash [SHA-256] : " + dsaHashService.hash("IoC Demo") );
        System.out.println("Random : " + randomService.randomHex(6) );
//        System.out.println( hashService.hash("IoC Demo") );
//        System.out.println( hashService.hashCode() + " " + hashService2.hashCode() );
//        System.out.println( md5HashService.hash("IoC Demo") );
    }
}
