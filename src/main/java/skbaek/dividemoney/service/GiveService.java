package skbaek.dividemoney.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skbaek.dividemoney.common.ExceptionString;
import skbaek.dividemoney.dto.MoneyGiveRequestDto;
import skbaek.dividemoney.dto.MoneyGiveResponseDto;
import skbaek.dividemoney.entity.MoneyGive;
import skbaek.dividemoney.entity.MoneyReceive;
import skbaek.dividemoney.repository.MoneyGiveRepository;
import skbaek.dividemoney.repository.MoneyReceiveRepository;
import skbaek.dividemoney.util.DivideUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Service
public class GiveService {

    private final MoneyGiveRepository moneyGiveRepository;
    private final MoneyReceiveRepository moneyReceiveRepository;

    @Transactional
    public MoneyGiveResponseDto save(MoneyGiveRequestDto requestDto) {

        if( requestDto.getGiveMoney() < requestDto.getRecieveMens() ) throw new IllegalArgumentException(ExceptionString.LACK_OF_MONEY.getMsg());

        MoneyGive moneyGive = requestDto.toEntity();

        List<Integer> list = DivideUtil.divideMoney(moneyGive);
        List<MoneyReceive> mrList = DivideUtil.devideListSet(list, moneyGive);

        moneyReceiveRepository.saveAll(mrList);

        return new MoneyGiveResponseDto(moneyGiveRepository.save(moneyGive));
    }

}
