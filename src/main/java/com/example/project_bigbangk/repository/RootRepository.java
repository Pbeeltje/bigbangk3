// Created by RayS
// Creation date 3-12-2021

/* Ray: RootRepository alvast gemaakt, bleek een methode in mijn voorbeelden te zitten waarin die werd aangeroepen.
 * Dus voor Address-Service maar gelijk meegenomen
 */

package com.example.project_bigbangk.repository;

import com.example.project_bigbangk.model.Client;
import com.example.project_bigbangk.model.RegistrationDTO;
import com.example.project_bigbangk.model.Wallet;
import org.springframework.stereotype.Repository;

@Repository
public class RootRepository {

   private IClientDAO clientDAO;
   private IAddressDAO addressDAO;
   private IWalletDAO walletDAO;

   public RootRepository(IClientDAO clientDAO, IAddressDAO addressDAO) {
      this.clientDAO = clientDAO;
      this.addressDAO = addressDAO;
   }

   // CLIENT

   public Client findClientByEmail(String email) {
      Client client = clientDAO.findClientByEmail(email);
      if (client == null) {
      }
      return null;
   }

   //FIXME kijken hoe dit precies werkt met ERD...kan je een client maken zonder address/ wallet..is wel nodig eigenlijk want je slaan ze nooit in een DB aanroep op!
   //waarschijnlijk gewoon alle connecties optioneel maken (of anders een raar systeem waar de DAO de gegerereerde SQL naar de root stuurd en die een grote querry maakt maar dat lijkt me een boel werk)
   public void createNewlyRegisteredClient(Client client){
      clientDAO.saveClient(client);
      //FIXME moet nog hier of in addressDAO opvangen wat er gebeurt als een address er al in staat, client check ik al in registratieservice
      addressDAO.save(client.getAddress());
      walletDAO.createNewWallet(client.getWallet());
   }


   // IBAN

   public Wallet findWalletByIban(String iban) {
      return walletDAO.findWalletByIban(iban);
   }
}
