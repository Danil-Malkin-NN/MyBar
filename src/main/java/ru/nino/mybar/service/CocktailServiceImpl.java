package ru.nino.mybar.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nino.mybar.dto.model.CocktailsModel;
import ru.nino.mybar.dto.show.CocktailDto;
import ru.nino.mybar.dto.show.CocktailIngredientDto;
import ru.nino.mybar.dto.show.CocktailUserIngredientsDto;
import ru.nino.mybar.entity.Cocktail;
import ru.nino.mybar.entity.CocktailStatus;
import ru.nino.mybar.entity.IdEntity;
import ru.nino.mybar.entity.user.User;
import ru.nino.mybar.mapper.impl.CocktailMapperImpl;
import ru.nino.mybar.repository.impl.CocktailRepositoryImpl;
import ru.nino.mybar.repository.impl.UserRepositoryImpl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Objects;

@Service
public class CocktailServiceImpl extends NameFindService<CocktailDto, Cocktail> {

    private final CocktailMapperImpl mapper;
    private final CocktailRepositoryImpl repository;
    private final UserRepositoryImpl userRepository;
    private final CocktailRatingService cocktailRatingService;

    public CocktailServiceImpl(CocktailRepositoryImpl repository, CocktailMapperImpl mapper,
                               UserRepositoryImpl userRepository, CocktailRatingService cocktailRatingService) {
        super(repository, mapper);
        this.mapper = mapper;
        this.repository = repository;
        this.userRepository = userRepository;
        this.cocktailRatingService = cocktailRatingService;
    }

    public Page<CocktailIngredientDto> getPageAll(Pageable pageable) {
        return repository.findByStatus(CocktailStatus.APPROVED, limitPageSize(pageable))
                .map(mapper::toIngredientDto);
    }

    public Page<CocktailsModel> getPageModels(Pageable pageable) {
        Page<Cocktail> all = repository.findAll(pageable);
        return all.map(mapper::toModel);
    }

    public List<CocktailUserIngredientsDto> searchByIngredientList(List<Integer> ingredients) {
        List<CocktailUserIngredientsDto> cocktailUserIngredientsDtos = repository.findByIngredientsList(ingredients)
                .stream()
                .map(mapper::toUserIngredients)
                .toList();

        cocktailUserIngredientsDtos.stream()
                .flatMap(cocktailUserIngredientsDto -> cocktailUserIngredientsDto.getIngredients()
                        .stream())
                .forEach(ingredientAvailableDto -> ingredientAvailableDto.setAvailable(
                        ingredients.contains(ingredientAvailableDto.getId())));

        return cocktailUserIngredientsDtos;
    }

    public Page<CocktailDto> searchByName(String name, Pageable pageable) {
        return repository.searchApprovedCatalog(blankToEmpty(name), "", null, null, null, null,
                        limitPageSize(pageable))
                .map(mapper::toDto);
    }

    @Override
    public List<CocktailDto> getAll() {
        return repository.findByStatusOrderByNameAsc(CocktailStatus.APPROVED)
                .stream().map(mapper::toDto).toList();
    }

    @Override
    public Page<CocktailDto> getPage(Pageable pageable) {
        return repository.searchApprovedCatalog("", "", null, null, null, null,
                        limitPageSize(pageable))
                .map(mapper::toDto);
    }

    @Override
    public CocktailDto getById(Long id) {
        return repository.findByIdAndStatus(id, CocktailStatus.APPROVED)
                .map(mapper::toDto)
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND, "Коктейль не найден"));
    }

    @Override
    public CocktailDto getByName(String name) {
        return repository.findFirstByNameAndStatus(name, CocktailStatus.APPROVED)
                .map(mapper::toDto)
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND, "Коктейль не найден"));
    }

    public Page<CocktailsModel> getPageWithNameFilterModel(String cocktailName, Pageable pageable, String email) {
        User userInfo = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Информация о пользователе: " + email + " не найдена"));

        Map<Long, List<Cocktail>> collect = userInfo.getFavoriteCocktail()
                .stream()
                .collect(Collectors.groupingBy(IdEntity::getId));

        Page<CocktailsModel> map = repository.findByNameContainingIgnoreCase(cocktailName, pageable)
                .map(mapper::toModel);

        map.stream()
                .filter(cocktailsModel -> collect.containsKey(cocktailsModel.getId()))
                .forEach(cocktailsModel -> cocktailsModel.setFavorite(true));

        return map;
    }

    public Page<CocktailsModel> getPageWithNameFilterModel(String cocktailName, Pageable pageable) {
        return repository.findByNameContainingIgnoreCase(cocktailName, pageable)
                .map(mapper::toModel);
    }

    public Page<CocktailsModel> searchApprovedCatalog(String name, String baseAlcohol,
                                                       Integer minStrength, Integer maxStrength,
                                                       Integer minVolume, Integer maxVolume,
                                                       Pageable pageable, String email) {
        Page<CocktailsModel> page = repository.searchApprovedCatalog(
                        blankToEmpty(name), blankToEmpty(baseAlcohol), minStrength, maxStrength,
                        minVolume, maxVolume, limitPageSize(pageable))
                .map(mapper::toModel);
        if (email == null || email.isBlank()) {
            return page;
        }
        userRepository.findByEmail(email).ifPresent(user -> {
            var favorites = user.getFavoriteCocktail().stream()
                    .map(IdEntity::getId)
                    .collect(Collectors.toSet());
            page.forEach(model -> model.setFavorite(favorites.contains(model.getId())));
        });
        return page;
    }

    public List<String> getApprovedBaseAlcohols() {
        return repository.findDistinctApprovedBaseAlcohols();
    }

    @Transactional(readOnly = true)
    public List<CocktailsModel> getTopRatedAuthorCocktails() {
        return cocktailRatingService.topRatedAuthorCocktails().stream()
                .map(cocktail -> {
                    CocktailsModel model = mapper.toModel(cocktail);
                    CocktailRatingService.RatingSummary rating =
                            cocktailRatingService.summary(cocktail.getId(), null);
                    model.setRatingAverage(rating.average());
                    model.setRatingCount(rating.count());
                    return model;
                })
                .toList();
    }

    public List<Cocktail> getApprovedCocktails() {
        return repository.findByStatusOrderByNameAsc(CocktailStatus.APPROVED);
    }

    public List<Cocktail> getApprovedCocktails(List<Long> ids) {
        return repository.findByIdInAndStatus(ids, CocktailStatus.APPROVED);
    }

    public List<Cocktail> searchApprovedCocktails(String name) {
        if (name == null || name.isBlank()) {
            return List.of();
        }
        return repository.findTop10ByNameContainingIgnoreCaseAndStatusOrderByNameAsc(
                name.trim(), CocktailStatus.APPROVED);
    }

    public List<Cocktail> getSubmissions(String author) {
        return repository.findBySubmittedByOrderByIdDesc(author);
    }

    public Cocktail getApprovedCocktail(Long id) {
        return repository.findByIdAndStatus(id, CocktailStatus.APPROVED)
                .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.NOT_FOUND, "Коктейль не найден"));
    }

    private String blankToEmpty(String value) {
        return value == null || value.isBlank() ? "" : value.trim();
    }

    private Pageable limitPageSize(Pageable pageable) {
        int size = Math.min(Math.max(pageable.getPageSize(), 1), 100);
        return org.springframework.data.domain.PageRequest.of(pageable.getPageNumber(), size, pageable.getSort());
    }

    @Override
    protected String getEntityName() {
        return "Cocktail";
    }
}
