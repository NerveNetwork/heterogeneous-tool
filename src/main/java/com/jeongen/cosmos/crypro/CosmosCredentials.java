package com.jeongen.cosmos.crypro;

import com.jeongen.cosmos.util.CosmosAddressUtil;
import io.netty.util.internal.StringUtil;
import lombok.Getter;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.wallet.DeterministicKeyChain;
import org.bitcoinj.wallet.DeterministicSeed;
import org.web3j.utils.Numeric;
import util.AddressUtil;
import util.HexUtil;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

@Getter
public class CosmosCredentials {

    private ECKey ecKey;
    private String address;
    private CosmosAddressUtil addressUtil;
    private boolean compressed;

    private CosmosCredentials() {

    }
    public static CosmosCredentials create(byte[] privateKey, CosmosAddressUtil addressUtil) {
        return create(privateKey, addressUtil, true);
    }

    public static CosmosCredentials create(byte[] privateKey, CosmosAddressUtil addressUtil, boolean compressed) {
        ECKey ecKey = ECKey.fromPrivate(new BigInteger(1, privateKey), compressed);
        return create(ecKey, addressUtil, compressed);
    }

    public static CosmosCredentials create(ECKey ecKey, CosmosAddressUtil addressUtil, boolean compressed) {
        CosmosCredentials credentials = new CosmosCredentials();
        credentials.ecKey = ecKey;
        credentials.addressUtil = addressUtil;
        credentials.compressed = compressed;
        if (compressed) {
            credentials.address = credentials.addressUtil.ecKeyToAddress(ecKey);
        } else {
            String pubKey = HexUtil.encode(ecKey.getPubKey());
            String ethAddress = AddressUtil.getEthAddressFromPubKey(pubKey);
            byte[] bytes = Numeric.hexStringToByteArray(ethAddress);
            credentials.address = addressUtil.convertAndEncode(bytes);
        }
        return credentials;
    }

    public static CosmosCredentials create(String mnemonic, String password, String derivePath, CosmosAddressUtil addressUtil) {
        if (StringUtil.isNullOrEmpty(derivePath)) {
            return null;
        }

        String[] mnemonicArr = mnemonic.split(" ");

        DeterministicSeed deterministicSeed = new DeterministicSeed(Arrays.asList(mnemonicArr), null, password, 0);
        DeterministicKeyChain deterministicKeyChain = DeterministicKeyChain.builder().seed(deterministicSeed).build();

        List<ChildNumber> childNumbers = addressUtil.decodePath(derivePath);
        DeterministicKey deterministicKey = deterministicKeyChain.getKeyByPath(childNumbers, true);

        return CosmosCredentials.create(deterministicKey, addressUtil, true);
    }

}
