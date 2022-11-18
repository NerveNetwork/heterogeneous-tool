package com.jeongen.cosmos.crypro;

import com.jeongen.cosmos.util.CosmosAddressUtil;
import io.netty.util.internal.StringUtil;
import lombok.Getter;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.wallet.DeterministicKeyChain;
import org.bitcoinj.wallet.DeterministicSeed;

import java.util.Arrays;
import java.util.List;

@Getter
public class CosmosCredentials {

    private ECKey ecKey;
    private String address;
    private CosmosAddressUtil addressUtil;

    private CosmosCredentials() {

    }

    public static CosmosCredentials create(ECKey ecKey, CosmosAddressUtil addressUtil) {
        CosmosCredentials credentials = new CosmosCredentials();
        credentials.ecKey = ecKey;
        credentials.addressUtil = addressUtil;
        credentials.address = credentials.addressUtil.ecKeyToAddress(ecKey);
        return credentials;
    }

    public static CosmosCredentials create(byte[] privateKey, CosmosAddressUtil addressUtil) {
        ECKey ecKey = ECKey.fromPrivate(privateKey);
        return create(ecKey, addressUtil);
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

        return CosmosCredentials.create(deterministicKey, addressUtil);
    }

}
