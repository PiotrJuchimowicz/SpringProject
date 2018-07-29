package comcompany.app.base.ServicesImp;

import comcompany.app.base.Models.Boss;
import comcompany.app.base.Repositories.GenericRepository;
import comcompany.app.base.Services.BossService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.ServiceMode;

@Service
public class BossServiceImpl extends GenericServiceImpl<Boss> implements BossService {

    @Autowired
    public void setRepository(GenericRepository<Boss> bossRepository) {
        this.setGenericRepository(bossRepository);
    }


}
