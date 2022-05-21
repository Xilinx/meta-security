# Copyright (C) 2022 Armin Kuster <akuster808@gmail.com>
#
import re

from oeqa.runtime.case import OERuntimeTestCase
from oeqa.core.decorator.depends import OETestDepends
from oeqa.runtime.decorator.package import OEHasPackage

class ParsecTest(OERuntimeTestCase):
    @OEHasPackage(['parsec-service'])
    @OETestDepends(['ssh.SSHTest.test_ssh'])
    def test_parsec_service(self):
        toml_file = '/etc/parsec/config.tom'
        status, output = self.target.run('echo library_path = "/usr/lib/softhsm/libsofthsm2.so" >> %s' %(toml_file))
        status, output = self.target.run('echo slot_number = 0 >> %s' %(toml_file))
        status, output = self.target.run('echo user_pin = "123456" >> %s' %(toml_file))
        cmds = [
                '/etc/init.d/parsec stop',
                'sleep 5',
                'softhsm2-util --init-token --slot 0 --label "Parsec Service" --pin 123456 --so-pin 123456',
                'for d in /var/lib/softhsm/tokens/*; do chown -R parsec $d; done', 
                'mkdir /tmp/myvtpm',
                'swtpm socket --tpmstate dir=/tmp/myvtpm --tpm2 --ctrl type=tcp,port=2322 --server type=tcp,port=2321 --flags not-need-init &',
                'export TPM2TOOLS_TCTI="swtpm:port=2321"',
                'tpm2_startup -c',
                'sleep 2',
                '/etc/init.d/parsec start',
                'parsec-cli-tests.sh'
               ]

        for cmd in cmds:
            status, output = self.target.run(cmd)
            self.assertEqual(status, 0, msg='\n'.join([cmd, output]))
